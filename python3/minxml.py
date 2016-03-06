################################################################################
# 	Utilities
################################################################################

def _is_simple_char( ch ):
	if ch == ' ':
		return true
	elif ch.isspace():
		return false
	else:
		return ch.isprintable()

# This funcction is disgusting - it is an attempt to convert a
# character into its unicode codepoint value. There must be
# a direct way of doing this; alas I couldn't find it while disconnected
# from the internet.
def _character2codepoint( ch ):
	b = ch.encode( 'utf-32' )
	return b[4] + 256 * ( b[5] + 256 * ( b[6] + 256 * b[7] ) )

def _codepoint2character( n ):
	b0 = n & 255
	n <<= 8
	b1 = n & 255
	n <<= 8
	b2 = n & 255
	n <<= 8
	b3 = n & 255
	return bytes( [b0,b1,b2,b3] ).decode( 'utf-32' )

def _escape_char( ch ):
	if ch == '"':
		return "&quot;"
	elif ch == '\'':
		return "&apos;"
	elif ch == '<':
		return "&lt;"
	elif ch == '>':
		return "&gt;" 
	elif ch == '&':
		return "&amp;" 
	elif _is_simple_char( ch ):
		return ch
	else:
		return '&#' + str( _character2codepoint( ch ) ) + ';'

# Helper function.
def _minxml_escape( text ):
	sofar = ''
	for ch in text:
		sofar += _escape_char( ch )
	return sofar


################################################################################
# 	Implementation of MinXML (equivalent to FlexiMinXML)
################################################################################

class MinXML:
	"""An implementation of Minimal XML - a clean subset of XML"""

	def __init__(self, typename, *kids):
		super(MinXML, self).__init__()
		self.typename = typename
		self.attributes = {}
		self.children = []
		self.children.extend( kids )

	def clear( self ):
		self.children.clear()

	def getName( self ):
		return self.typename

	def hasName( self, name ):
		return self.typename == name

	# Helper method for __str__
	def start_tag( self, list ):
		list.append( '<' )
		list.append( self.typename )
		for ( k, v ) in self.attributes.items():
			list.append( ' ' )
			list.append( k )
			list.append( '="' )
			list.append( _minxml_escape( v ) )
			list.append( '"' )
		list.append( '>' if self.children else '/>' )

	# Helper method for __str__
	def end_tag( self, list ):
		list.append( '</' )
		list.append( self.typename )
		list.append( '>' )

	# Helper method for __str__
	def strlist( self, list ):
		if not self.children:
			self.start_tag( list )
		else:
			self.start_tag( list )
			for k in self.children:
				k.strlist( list )
			self.end_tag( list )

	def __str__( self ):
		sofar = []
		self.strlist( list )
		return ''.join( list )

	def add( self, minx ):
		self.children.append( minx )

	def __getitem__( self, n ):
		return self.children[ n ]

	def __setitem__( self, n, value ):
		self.children[ n ] = value

	def __len__( self ):
		return len( self.children )

	def getFirst( self ):
		return self.children[ 0 ]

	def getChild( self, n ):
		return self.children[ n ]

	def getLast( self ):
		return self.children[ -1 ]

	def get( self, key, otherwise=KeyError ):
		try:
			return self.attributes[ key ]
		except KeyError:
			if otherwise == KeyError:
				raise KeyError
			return otherwise

	def put( self, key, value ):
		self.attributes[ key ] = value


################################################################################
# 	MinXML builder class
################################################################################

class Builder():
	
	def __init__( self ):
		self.current_element = MinXML( "DUMMY_NODE" )
		self.element_stack = []

	def startTagOpen( self, name = None ):
		self.element_stack.append( self.current_element )
		self.current_element = MinXML( name or "" )

	def put( self, key, value ):
		self.current_element.put( key, value )
	
	def _bindName( self, name ):
		if name:
			if self.current_element.hasName( "" ):
				self.current_element.setName( name );
			elif not self.current_element.hasName( name ):
				raise Exception( "Mismatched tags", { "Expected": self.current_element.getName(), "Actual": name } )

	def startTagClose( self, name = None ):
		self._bindName( name )

	def endTag( self, name = None ):
		self._bindName( name )
		b2 = self.element_stack.pop()
		b2.add( self.current_element )
		self.current_element = b2

	def	build( self ):
		if not self.current_element:
			return None
		else:
			result = self.current_element.getLast()
			self.current_element.clear()
			return result


################################################################################
# 	Implementation of MinXML parser.
################################################################################

class Source():

	def __init__( self, source ):
		self.source = source
		self.prev = None

	def fetch( self, otherwise ):
		ch = self.source.read( 1 )
		if ch:
			return ch
		if otherwise != None:
			return otherwise
		raise Exception( "Unexpected end of file" ) 

	def fetch_and_cache( self, otherwise ):
		self.prev = self.fetch( otherwise )
		return self.prev

	def nextChar( self, otherwise = None ):
		if self.prev:
			ch = self.prev
			self.prev = None
			return ch
		else:
			return self.fetch( otherwise )

	def pushChar( self, ch ):
		if self.prev:
			raise Exception( "No room to push another character", { 'Pushing': ch } )
		else:
			self.prev = ch

	def peekChar( self, otherwise = None ):
		if self.prev:
			return self.prev
		else:
			return self.fetch_and_cache( otherwise )

	def isNextChar( self, ch ):
		if self.prev:
			return ch == self.prev
		else:
			return ch == self.advance()

	def skipChar( self ):
		if self.prev:
			self.prev = None
		else:
			self.source.read( 1 )

	def hasNextChar( self ):
		return self.peekChar( '' )

	def eatUpTo( self, stop_char ):
		while stop_char != self.nextChar():
			pass

	def eatSpace( self ):
		if self.prev:
			if self.prev.isspace():
				self.prev = None
			else:
				return
		while True:
			ch = self.source.read( 1 )
			if not ch:
				return
			if not ch.isspace():
				self.prev = ch
				return

	def tryReadChar( self, want_ch ):
		if want_ch == self.peekChar( '' ):
			self.skipChar()
			return True
		else:
			return False

	def mustReadChar( self, ch ):
		actual = self.nextChar()
		if actual != ch:
			raise Exception( "Unexpected character", { 'Wanted': ch, 'Actual': actual } )
	
def _readAttributeValue( cucharin ):
	attr = []
	q = cucharin.nextChar()
	if q != '"' and q != '\'':
		raise Exception( "Attribute value not quoted", { "Character": q } )
	while True:
		ch = cucharin.nextChar()
		if ch == q:
			break;
		if ch == '&':
			attr.append( _readEscape( cucharin ) )
		else:
			if ch == '<':
				raise Exception( "Forbidden character in attribute value", { "Character": ch } )
			attr.append( ch )
	return ''.join( attr )

def _readEscapeContent( cucharin ):
	esc = []
	while True:
		ch = cucharin.nextChar()
		if ch == ';':
			break;
		esc.append( ch )
		if len( esc ) > 4:
			raise Exception( "Malformed escape", { "Sequence so far": esc } )
	return ''.join( esc )

_entity_table = {
	'lt': '<',
	'gt': '>',
	'amp': '&',
	'quot': '"',
	'apos': "'"
}

def _entityLookup( symbol ):
	return _entity_table[ symbol ]

def _is_name_char( ch ):
	return ch.isalnum() or ch == '-' or ch == '.'

def _readEscape( cucharin ):
	esc = _readEscapeContent( cucharin )
	if len( esc ) >= 2 and esc[0] == '#':
		try:
			n = str( esc[1:] )
			return _codepoint2character( n )
		except ValueError:
			raise Exception( "Unexpected numeric sequence after &#", { "Sequence": esc } )
	else:
		return _entityLookup( esc )

def _readName( cucharin ):
	name = []
	while cucharin.hasNextChar():
		ch = cucharin.nextChar()
		if _is_name_char( ch ):
			name.append( ch )
		else:
			cucharin.pushChar( ch )
			break
	return ''.join( name )

def _eatComment( cucharin, ch ):
	if ch == '!':
		# <!-- .... -->
		if cucharin.tryReadChar( '-' ):
			cucharin.mustReadChar( '-' )
			count_minuses = 0
			while True:
				nch = cucharin.nextChar()
				if nch == '-':
					count_minuses += 1
				elif nch == '>' and count_minuses >= 2:
					break
				else:
					if count_minuses >= 2:
						raise Exception( "Invalid XML comment (while in body of comment)", { "Character following --": nch } )
					count_minuses = 0
		else:
			# <! ...... >
			while True:
				nch = cucharin.nextChar()
				if nch == '>':
					break
				if nch == '<':
					_eatComment( cucharin, cucharin.nextChar() )
	else:
		# <? .... >
		cucharin.eatUpTo( '>' )

def _read1Attribute( cucharin ):
	cucharin.eatSpace()
	c = cucharin.peekChar();
	if c == '/' or c == '>':
		return None
	key = _readName( cucharin )
	cucharin.eatSpace()
	cucharin.mustReadChar( '=' )
	cucharin.eatSpace()
	value = _readAttributeValue( cucharin )
	return ( key, value )

class Parser():

	def __init__( self, source ):
		self.level = 0
		self.cucharin = Source( source )
		self.pending_end_tag = False
		self.parent = Builder()
		self.tag_name = None

	def _processAttributes( self ):
		while True:
			kv = _read1Attribute( self.cucharin )
			if not kv:
				break
			( k, v ) = kv
			self.parent.put( k, v )

	def readSingleTag( self ):
		
		if self.pending_end_tag:
			self.parent.endTag( self.tag_name )
			self.pending_end_tag = False
			self.level -= 1
			return True
			
		self.cucharin.eatSpace()
		
		if not self.cucharin.hasNextChar():
			return False
		
		self.cucharin.mustReadChar( '<' )
			
		ch = self.cucharin.nextChar()
		if ch == '/':
			end_tag = _readName( self.cucharin )
			self.cucharin.eatSpace();
			self.cucharin.mustReadChar( '>' )
			self.parent.endTag( end_tag )
			self.level -= 1
			return True
		elif ch == '!' or ch == '?':
			_eatComment( self.cucharin, ch )
			return self.readSingleTag()
		else:
			self.cucharin.pushChar( ch )
		
		self.tag_name = _readName( self.cucharin )
		self.parent.startTagOpen( self.tag_name );
		self._processAttributes();
		self.parent.startTagClose( self.tag_name );
		
		self.cucharin.eatSpace()
				
		ch = self.cucharin.nextChar()
		if ch == '/':
			self.cucharin.mustReadChar( '>' )
			self.pending_end_tag = True
			self.level += 1
			return True
		elif ch == '>':
			self.level += 1
			return True
		else:
			raise Exception( "Invalid continuation" )

	# Read an element off the input stream or null if the stream is
	# exhausted.
	# @return the next element
	def readElement( self ):
		while self.readSingleTag() and self.level != 0:
			pass
		if self.level != 0:
			raise Exception( "Unmatched tags due to encountering end of input" );
		return self.parent.build()	

################################################################################

# if __name__ == "__main__":
# 	xxx = MinXML( "foo" )
# 	yyy = MinXML( "bar" )
# 	zzz = MinXML( "gort" )
# 	xxx.add( yyy )
# 	xxx.add( zzz )
# 	xxx.put( "alpha", "A" )
# 	xxx.put( "beta", "B" )
# 	xxx.put( "beta", "<>&" )
# 	bbb = Builder()