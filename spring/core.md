#Dependency injection

> Dependency injection (DI) is a process whereby objects define their dependencies, that is, the other objects they work with, only through constructor arguments, arguments to a factory method, or properties that are set on the object instance after it is constructed or returned from a factory method. The container then injects those dependencies when it creates the bean. This process is fundamentally the inverse, hence the name Inversion of Control (IoC), of the bean itself controlling the instantiation or location of its dependencies on its own by using direct construction of classes, or the Service Locator pattern.

声明自己的依赖，由Container来初始化自己

> Code is cleaner with the DI principle and decoupling is more effective when objects are provided with their dependencies. The object does not look up its dependencies, and does not know the location or class of the dependencies. As such, your classes become easier to test, in particular when the dependencies are on interfaces or abstract base classes, which allow for stub or mock implementations to be used in unit tests.

不知道自己以外对象的位置，方便解耦、mock和test


[Spring Core](https://docs.spring.io/spring/docs/5.1.2.RELEASE/spring-framework-reference/core.html#spring-core)

> In Spring, the objects that form the backbone of your application and that are managed by the Spring IoC container are called beans. A bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container. Otherwise, a bean is simply one of many objects in your application. Beans, and the dependencies among them, are reflected in the configuration metadata used by a container.

在Spring中对象构成了应用的骨架，并且被Io容器管理，这些对象就是beans。一个bean由Ioc容器初始化、调配和管理。其他方面bean只是众多对象之一。beans以及对象之间的依赖关系由容器的配置文件来表达



