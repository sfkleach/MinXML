/// Dict is an compact and efficient dictionary for storing values indexed
/// by strings. The implementation is based on balanced trees. It supports 
/// access and update operations in log(N) times.
/// 

pub struct Dict {
    root: Node,
}

enum Node {
    Empty,
    Leaf(String, i32),
    Internal(Box<InternalNode>),
}



impl Dict {
    /// Creates a new empty dictionary.
    pub fn new() -> Dict {
        Dict {
            root: Node::Empty,
        }
    }

    /// Inserts a new key-value pair into the dictionary. If the key already
    /// exists, the old value is replaced with the new value.
    pub fn insert(&mut self, key: &str, value: i32) {
        self.root.insert(key, value);
    }

    /// Returns the value associated with the given key, or None if the key
    /// does not exist in the dictionary.
    pub fn get(&self, key: &str) -> Option<i32> {
        self.root.get(key)
    }

    /// Removes the key-value pair with the given key from the dictionary.
    pub fn remove(&mut self, key: &str) {
        self.root.remove(key);
    }
}