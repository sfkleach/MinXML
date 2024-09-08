use std::collections::HashMap;

use crate::element::Element;

pub struct ElementBuilder {
    stack: Vec<(Box<HashMap<String, String>>, Element)>
}

impl ElementBuilder {
    pub fn new() -> Self {
        ElementBuilder {
            stack: Vec::new()
        }
    }

    pub fn start_tag(&mut self, name: &str) {
        let element = Element::new(name);
        let attrs = Box::new(HashMap::new());
        self.stack.push((attrs, element));
    }

    pub fn put(&mut self, key: &str, value: &str) {
        if let Some(mut pair) = self.stack.pop() {
            pair.0.insert(key.to_string(), value.to_string());
        }
    }

    pub fn end_tag(&mut self, name: Option<&str>) -> Result<Option<Element>, String> {
        if let Some(pair) = self.stack.pop() {
            let (attrs, mut element) = pair;
            if name.is_none() || element.has_name(name.unwrap()) {
                element.set_attributes(*attrs);
                if let Some(parentpair) = self.stack.last_mut() {
                    let (_, parent) = parentpair;
                    parent.add_child(element);
                    Ok(None)
                } else {
                    Ok(Some(element))
                }
            } else {
                Err(format!("Mismatched end tag: expected </{}> but found </{}>", element.name(), name.unwrap()))
            }
        }
        else {
            Err("No element to close.".to_string())
        }
    }
}
