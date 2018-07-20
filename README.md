Hairspray
=========

Hairspray is a simple fixture replacement library. Inspired by factory_bot.
This library makes it easy to create fixtures.

## Install

Add dependency to pom.xml:

```xml
<dependency>
    <groupId>com.github.wiro34</groupId>
    <artifactId>hairspray-jpa</artifactId>
    <version>0.2.3</version>
</dependency>
```

If you don't use JPA, please add the following dependency.

```xml
<dependency>
    <groupId>com.github.wiro34</groupId>
    <artifactId>hairspray-core</artifactId>
    <version>0.2.3</version>
</dependency>
```

## Getting Started

### Configure EntityManager Producer

Create an EntityManager producer in test source:

```java
@ApplicationScoped
public class EntityManagerProducer {
    @Produces
    @PersistenceContext(unitName = "YOUR_PERSISTENCE_UNIT_NAME")
    private EntityManager entityManager;
}
```

### Defining Factories

Each factory has a partial set of fields that the target class fields.

For example:

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private Integer age;

    private Sex sex;
    
    @OneToMany
    private List<Post> posts;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public static enum Sex {
        MALE, FEMALE
    }
}
```

```java
@Factory(User.class)
public class UserFactory {
    public String firstName = "John";
    public String lastName = "Doe";
    public Integer age = 18;
}
```

### Dynamic Attributes

Most factory attributes add values ​​to the instance that are evaluated when the factory is defined.
If you need dynamically configured values, such as association or random values, you can define and add instances of the Dynamic interface.
If you need a dynamic attribute, such as association or random value, you can define an instance of [Dynamic](core/src/main/java/com/github/wiro34/hairspray/Dynamic.java) interface.

```java
@Factory(User.class)
public class UserFactory {
    public Dynamic<User, Sex> sex = (user) -> user.getFirstName().equals("Jane") ? Sex.FEMALE : Sex.MALE;
}
```

### Association

If you want to use an instance created by another factory in the field, you can define it using `Dynamic`.
The factory class is CDI managed, so you can inject an instance of `Hairspray`.

```java
@Factory(User.class)
public class UserFactory {
    @Inject
    private Hairspray factory;
 
    public Dynamic<User, List<Post>> posts = (user) -> factory.createList(Post.class, 2);
}
```

### Using factories

Hairspray supports build and create strategies.

```java
// Returns a User instance that's not persisted
User user = factory.build(User.class);

// Returns a list of User instances that's not persisted
List<User> user = factory.buildList(User.class, 3);

// Returns a persisted User instance
User user = factory.create(User.class);

// Returns a persisted list of User instances
List<User> user = factory.createList(User.class, 3);
```

It's possible to override the defined attributes by Consumer:

```java
User user = factory.create(User.class, (user) -> {
    user.setFirstName("Jane");
});
```

### Example

Inject `Hairspray` instance into test class.

```java
public class ExampleTest {
    @Inject
    private Hairspray factory;

    @Test
    public void test1() {
        User user = factory.create(User.class);
        assertEquals(user.getFullName(), "John Doe");
        assertEquals(user.getSex(), Sex.MALE);
    }

    @Test
    public void test2() {
        // overwrite first name
        user = factory.create(User.class, (u) -> {
            u.setFirstName("Jane");
        });
        assertEquals(user.getFullName(), "Jane Doe");
        assertEquals(user.getSex(), Sex.FEMALE);
    }
}
```

and more

see [hairspray-example](https://github.com/wiro34/hairspray-example)

## TODO

- [ ] Annotation based association support
- [ ] Sequence number generator
