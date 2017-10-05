= SeBi Venlo State Walker

Pieter van den Hombergh (homberghp) <pieter.van.den.hombergh@gmail.com>
Version 1.0

# StateWalker will simplify the coding of your (complex) state machines.

Just add a Java 8 interface with default methods for all methods or events you states should accept. Have an enum implement this interface and give it at least  a `NULL` value.


```Java
class State extends StateBase {
	
}
```

```Java
enum MyStates extends State {
     NULL,
     S1{
     ...
     },
     S2{
     ...
     },
     ;
}
```
