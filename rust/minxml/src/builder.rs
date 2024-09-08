use crate::element::Element;

pub struct ElementBuilder {
    stack: Vec<Element>,
}

impl ElementBuilder {
    pub fn new() -> Self {
        ElementBuilder {
            stack: Vec::new(),
        }
    }

    pub fn start_tag(&mut self, name: &str) {
        let element = Element::new(name);
        self.stack.push(element);
    }

    pub fn put(&mut self, key: &str, value: &str) {
        if let Some(mut element) = self.stack.pop() {
            element.add_attribute(key, value);
        }
    }

    pub fn end_tag(&mut self, name: Option<&str>) -> Result<Option<Element>, String> {
        if let Some(mut element) = self.stack.pop() {
            if name.is_none() || element.has_name(name.unwrap()) {
                if let Some(parent) = self.stack.last_mut() {
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
