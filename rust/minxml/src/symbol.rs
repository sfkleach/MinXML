use std::collections::HashMap;
use std::sync::{Arc, Mutex};
use std::hash::{Hash, Hasher};
use std::fmt;

/// A wrapper for uniquely allocated strings.
#[derive(Clone, Eq)]
pub struct Symbol(Arc<String>);

impl Symbol {
    /// Creates a new `Symbol` from the given string slice.
    pub fn new(s: &str) -> Symbol {
        // Use a global interning mechanism to ensure unique allocation.
        INTERNER.lock().unwrap().intern(s)
    }

    /// Returns the string slice of the `Symbol`.
    pub fn as_str(&self) -> &str {
        &self.0
    }
}

impl PartialEq for Symbol {
    fn eq(&self, other: &Self) -> bool {
        Arc::ptr_eq(&self.0, &other.0)
    }
}

impl Hash for Symbol {
    fn hash<H: Hasher>(&self, state: &mut H) {
        self.0.hash(state)
    }
}

impl fmt::Debug for Symbol {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "Symbol({:?})", self.0)
    }
}

/// A global interner for `Symbol` instances.
struct Interner {
    map: HashMap<String, Arc<String>>,
}

impl Interner {
    /// Interns the given string slice and returns a `Symbol`.
    fn intern(&mut self, s: &str) -> Symbol {
        if let Some(existing) = self.map.get(s) {
            return Symbol(existing.clone());
        }
        let arc_str = Arc::new(s.to_string());
        self.map.insert(s.to_string(), arc_str.clone());
        Symbol(arc_str)
    }
}

lazy_static::lazy_static! {
    static ref INTERNER: Mutex<Interner> = Mutex::new(Interner {
        map: HashMap::new(),
    });
}

