MinXML
======

Minimal XML - a cut down version of XML; simpler, purer and better. 

What Is It?
-----------

Minimal XML is a cut down subset of XML suitable for representing hierarchical or 'tree-like' data. In particular it leaves out:

* Character data - that's not included.
* Comments - they are allowed but discarded.
* Processing instructions - they are allowed but discarded.
* Entities - only numerical entities and the standard entities &lt; &gt; &quot; &apos; &amp; are recognised.
* Character encoding - the API requires streams of decoded characters.

What remains?
-------------

* Start tags, with attributes.
* End tags.
* Empty (or "fused") tags, with attributes.

See the [MinXML Grammar](http://steelypip.wikidot.com/minxml-grammar) for a formal description.

Why?
----

MinXML is syntax neutral. By “syntax neutral” we mean that we have a no-frills data format that can be processed with reasonable ease in a very wide variety of programming languages, can be read by programmers and, at a pinch, written as well. We mean that it is free of features that strongly favour people from one particular background. In other words, we have aimed to make it accessible to a very wide range of people, without bias, to the best of our judgement.

So what was the motivation behind stripping XML down to create MinXML? XML was a good starting point because it is designed to be machine processable, has become very widely known and there are a lot of languages providing XML support out of the box. But there were two basic reasons that strongly pushed in the direction of no-frills.

Firstly, as soon as you start writing programs to that use XML to represent data, you realise it is a surprisingly complicated format. Not only do you have to worry about extraneous features such as processing directives but also management issues such as validation against schemas (and what format will the schema be in?) When all you want to do is represent data, you become engaged in complexities that aren’t relevant - such as CDATA blocks.

Secondly, full XML has some generally undesirable properties. For example, you cannot safely indent XML because it is not safe to insert or remove whitespace between tags.  Another example is that entity processing requires fetching a DTD, which introduces a performance hit and extra fragility. By contrast MinXML was designed to just represent data in a simple, standalone way and having a dependency on another format isn't really acceptable, not even for the privilege of adding human-friendly symbols.

So we stripped away everything we could do without, except for comments. We retained comments because the JSON experience shows that omitting them is too extreme. However, we mandate that MinX comments are discarded on reading - they are annotations for people, not machines - and we don’t want our programs cluttered up with the consideration of whether or not they should process comments.

Advantages
----------

The first advantage of this format is that the internal representation is very neat; it is a tree of dictionaries, although the element name is only a single tag. Representing this in code is simple and the iteration idioms are simple. Here's the core of a naive implementation in Java.

```java
class MinX implements Iterable< MinX > {
    String name;
    Map< String, String > attributes;
    List< MinX > children;

    @Override
    public Iterator< MinX > iterator() {
        return children.iterator();
    }

}
```

The second advantage is that a MinXML document is completely self-contained.

  1. It can be correctly parsed without reference to a DTD.
  2. It has no constants to resolve.
  3. And can be completely parsed without any semantic analysis.

And this simplicity leads to both simpler implementations and simpler processing code. In particular, and in contrast to JSON, it is suited to languages with inflexible static typing, such as C++ and Java.

See http://steelypip.wikidot.com/minxml-examples for examples.

Disadvantages
-------------

The disadvantage is that it is very verbose and that basic types such as integers end up being encoded rather clumsily i.e. `<constant type="int" value="-3"/>`. And, of course, it is not XML.