use std::rc::Rc;
use crate::symbol::Symbol;


/// Represents an XML-like element with a mutable state.
#[derive(Clone)]
pub enum Element {
    State( Rc<ElementState> )
}

/// Holds the state of an Element, including its name, attributes, and children.
#[derive(Clone)]
pub struct ElementState {
    /// The name of the element.
    name: Symbol,
    /// The attributes of the element as key-value pairs.
    attributes: Vec<(Symbol, String)>,
    /// The children of the element.
    children: Vec<Element>
}

/// Creates a new Element with the given name.
/// 
/// # Arguments
/// 
/// * `name` - A string slice that holds the name of the element.
/// 
/// # Returns
/// 
/// * `Element` - A new Element instance with the specified name.
pub fn new_element(name: &str) -> Element {
    Element::State(Rc::new(ElementState {
        name: Symbol::new(name),
        attributes: Vec::new(),
        children: Vec::new()
    }))
}

impl Element {
    pub fn new(name: &str) -> Self {
        Element::State(Rc::new(ElementState {
            name: Symbol::new(name),
            attributes: Vec::new(),
            children: Vec::new()
        }))
    }

    /// Adds a child element to the given parent element.
    /// 
    /// # Arguments
    /// 
    /// * `element` - A reference to the parent Element.
    /// * `child` - The child Element to be added
    pub fn add_child(&mut self, child: Element) {
        let Element::State(state) = self;
        Rc::make_mut(state).children.push(child);
    }

    // Adds an attribute to the current element.
    pub fn add_attribute(&mut self, key: &str, value: &str) {
        let key = Symbol::new(key);
        let value = value.to_string();
        let Element::State(state) = self;
        Rc::make_mut(state).attributes.push((key, value));
    }

    /// Returns the name of the given element.
    pub fn name(self) -> String {
        let Element::State(state) = self;
        String::from(state.name.as_str())
    }

    pub fn has_name(self, name: &str) -> bool {
        let Element::State(state) = self;
        state.name.as_str() == name
    }

    pub fn symbol(self) -> Symbol {
        let Element::State(state) = self;
        state.name.clone()
    }

    /// Returns the attributes of the given element as a map, where the keys are the 
    /// attribute names and the values are the attribute values.
    pub fn attributes(self) -> std::collections::HashMap<String, String> {
        let Element::State(state) = self;
        state.attributes.iter()
            .map(|(key, value)| (key.as_str().to_string(), value.clone()))
            .collect()
    }

    /// Returns the children of the given element.
    pub fn children(self) -> Vec<Element> {
        let Element::State(state) = self;
        state.children.clone()
    }

    /// Returns the number of children of the given element.
    pub fn child_count(self) -> usize {
        let Element::State(state) = self;
        state.children.len()
    }

    /// Returns the child element at the given index.
    pub fn child_at(self, index: usize) -> Option<Element> {
        let Element::State(state) = self;
        state.children.get(index).cloned()
    }

    /// Returns the attribute value for the given key.
    pub fn attribute(self, key: &str) -> Option<String> {
        let Element::State(state) = self;
        state.attributes.iter()
            .find(|(k, _)| k.as_str() == key)
            .map(|(_, v)| v.clone())
    }

    pub fn has_attribute(self, key: &str) -> bool {
        let Element::State(state) = self;
        state.attributes.iter()
            .any(|(k, _)| k.as_str() == key)
    }

    pub fn has_attribute_symbol(self, key: Symbol) -> bool {
        let Element::State(state) = self;
        state.attributes.iter()
            .any(|(k, _)| k == &key)
    }
}


