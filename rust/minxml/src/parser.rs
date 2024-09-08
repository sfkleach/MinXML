use std::iter::Peekable;
use std::str::Chars;

use crate::builder::ElementBuilder;
use crate::element::Element;

struct Parser<'a> {
    input: Peekable<Chars<'a>>,
    builder: ElementBuilder,
}

impl<'a> Parser<'a> {
    fn new(input: &'a str) -> Self {
        Parser {
            input: input.chars().peekable(),
            builder: ElementBuilder::new(),
        }
    }

    pub fn parse(&mut self) -> Result<Element, String> {
        self.parse_element();
        let r = self.builder.build().unwrap();
        Ok(r)
    }

    fn parse_element(&mut self) -> Result<(), String> {
        self.consume_whitespace();
        self.consume_char('<')?;
        let name = self.parse_identifier()?;
        self.builder.start_tag_open(&name);
        self.parse_attributes()?;
        self.consume_whitespace();
        self.consume_char('>')?;

        let children = self.parse_children()?;

        self.consume_whitespace();
        self.consume_char('<')?;
        self.consume_char('/')?;
        let end_name = self.parse_identifier()?;
        if end_name != name {
            return Err(format!("Mismatched end tag: expected </{}> but found </{}>", name, end_name));
        }
        self.consume_char('>')?;

        Ok(())
    }

    fn parse_attributes(&mut self) -> Result<(), String> {
        loop {
            self.consume_whitespace();
            if self.peek_char() == Some('>') {
                break;
            }
            let name = self.parse_identifier()?;
            self.consume_whitespace();
            self.consume_char('=')?;
            self.consume_whitespace();
            let value = self.parse_quoted_string()?;
            self.builder.put(&name, &value);
        }
        self.builder.start_tag_close();
        Ok(())
    }

    fn parse_children(&mut self) -> Result<Vec<Element>, String> {
        let mut children = Vec::new();
        loop {
            self.consume_whitespace();
            if self.peek_char() == '<' && self.peek_next_char() == '/' {
                break;
            }
            children.push(self.parse_element()?);
        }
        Ok(children)
    }

    fn parse_identifier(&mut self) -> Result<String, String> {
        let start = self.position;
        while self.peek_char().is_alphanumeric() {
            self.position += 1;
        }
        if start == self.position {
            return Err("Expected identifier".to_string());
        }
        Ok(self.input[start..self.position].to_string())
    }

    fn parse_quoted_string(&mut self) -> Result<String, String> {
        self.consume_char('"')?;
        let start = self.position;
        while self.peek_char() != '"' {
            self.position += 1;
        }
        let value = self.input[start..self.position].to_string();
        self.consume_char('"')?;
        Ok(value)
    }

    fn consume_whitespace(&mut self) {
        while self.peek_char().is_whitespace() {
            self.position += 1;
        }
    }

    fn consume_char(&mut self, expected: char) -> Result<(), String> {
        if self.peek_char() == Some(expected) {
            self.input.next();
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

    fn peek_next_char(&mut self) -> Option<char> {
        let mut iter = self.input.clone();
        iter.next();
        iter.peek().copied()
    }
}

fn main() {
    let xml = r#"<root attr="value"><child></child></root>"#;
    let mut parser = Parser::new(xml);
    match parser.parse() {
        Ok(element) => println!("{:#?}", element),
        Err(e) => eprintln!("Error: {}", e),
    }
}