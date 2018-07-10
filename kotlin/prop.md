
> https://stackoverflow.com/questions/43220140/whats-kotlin-backing-field-for/43220314

Because, say if you don't have field keyword, you won't be able to actually set/get the value in the  get() or set(value). It enables you to access the backing field in the custom accessors.

This is the equivalent Java code of your sample:

class Sample {
    private int counter = 0;
    public void setCounter(int value) {
        if (value >= 0) setCounter(value);
    }
    public int getCounter() {
        return counter;
    }
}
Apparently this is not good, as the setter is just an infinte recursion into itself, never changing anything. Remember in kotlin whenever you write foo.bar = value it will be translated into a setter call instead of a PUTFIELD.

EDIT: Java has fields while Kotlin has properties, which is a rather higher level concept than fields.

There are two types of properties: one with a backing field, one without.

A property with a backing field will store the value in the form of a field. That field makes storing value in memory possible. An example of such property is the first and second properties of Pair. That property will change the in-memory representation of Pair.

A property without a backing field will have to store their value in other ways than directly storing it in memory. It must be computed from other properties, or, the object itself. An example of such property is the indices extension property of List, which is not backed by a field, but a computed result based on size property. So it won't change the in-memory representation of List (which it can't do at all because Java is statically typed).


