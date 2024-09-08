use std::iter::Peekable;
use std::str::Chars;

use crate::builder::ElementBuilder;
use crate::element::Element;

struct Parser<'a> {
    input: Peekable<Chars<'a>>,
    builder: ElementBuilder,
    root: Option<Element>,
}

impl<'a> Parser<'a> {
    fn new(input: &'a str) -> Self {
        Parser {
            input: input.chars().peekable(),
            builder: ElementBuilder::new(),
            root: None,
        }
    }

    pub fn parse(&mut self) -> Result<Element, String> {
        self.parse_element();
        if let Some(root) = self.root {
            Ok(root)
        } else {
            Err("No root element found".to_string())
        }
    }

    fn parse_element(&mut self) -> Result<(), String> {
        self.consume_whitespace();
        self.consume_char('<')?;
        let name = self.read_identifier()?;
        self.builder.start_tag(&name);
        self.parse_attributes()?;
        self.consume_whitespace();
        self.consume_char('>')?;

        self.parse_children()?;

        self.consume_whitespace();
        self.consume_char('<')?;
        self.consume_char('/')?;
        let end_name = self.read_identifier()?;
        if end_name != name {
            return Err(format!("Mismatched end tag: expected </{}> but found </{}>", name, end_name));
        }
        self.consume_char('>')?;
        self.root = self.builder.end_tag(Some(&name))?;

        Ok(())
    }

    fn parse_attributes(&mut self) -> Result<(), String> {
        loop {
            self.consume_whitespace();
            if self.peek_char() == Some('>') {
                break;
            }
            let name = self.read_identifier()?;
            self.consume_whitespace();
            self.consume_char('=')?;
            self.consume_whitespace();
            let value = self.read_quoted_string()?;
            self.builder.put(&name, &value);
        }
        Ok(())
    }

    fn parse_children(&mut self) -> Result<(), String> {
        loop {
            self.consume_whitespace();
            if self.input.peek() == Some(&'<') && self.input.peek() == Some(&'/') {
                break;
            }
            self.parse_element()?;
        }
        Ok(())
    }

    fn read_identifier(&mut self) -> Result<String, String> {
        let mut s = String::new();
        while let Some(ch) = self.input.peek() {
            if ch.is_alphanumeric() {
                s.push(ch.clone());
                self.input.next();
            } else {
                break;
            }
        }
        Ok(s)
    }

    fn read_quoted_string(&mut self) -> Result<String, String> {
        let mut s: String = String::new();
        self.consume_char('"')?;

        while let Some(ch) = self.input.next() {
            if ch == '"' {
                break;
            }
            s.push(ch);
        }

        Ok(s)
    }

    fn consume_whitespace(&mut self) {
        while let Some(ch) = self.peek_char() {
            if !ch.is_whitespace() {
                break;
            }
            self.input.next();
        }
    }

    fn consume_char(&mut self, expected: char) -> Result<(), String> {
        if self.input.next_if_eq(&expected).is_some() {
            Ok(())
        } else {
            Err(format!(
                "Expected '{}', found '{:?}'",
                expected,
                self.peek_char()
            ))
        }
    }

    fn peek_char(&mut self) -> Option<char> {
        self.input.peek().copied()
    }
}
