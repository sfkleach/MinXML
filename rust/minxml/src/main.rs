/// This uses the parser::Parser class to parse an XML-like string and print the result.
fn main() {
    let xml = r#"<root attr="value"><child></child></root>"#;
    let mut parser = Parser::new(xml);
    match parser.parse() {
        Ok(children) => {
            for child in children {
                println!("{:?}", child);
            }
        }
        Err(e) => {
            eprintln!("Error: {}", e);
        }
    }
}
