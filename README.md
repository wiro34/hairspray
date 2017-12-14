Hairspray
=========

Hairspray is a fixture library for testing like ~~factory_girl~~ factory_bot.
The library can creating fixtures easily.

## Usage

Add dependency to pom.xml:

```xml
<dependency>
    <groupId>com.github.wiro34</groupId>
    <artifactId>hairspray</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Getting Start

Create an EntityManager producer in test source:

```java
@ApplicationScoped
public class EntityManagerProducer {
    @Produces
    @PersistenceContext(unitName = "your persistence unit name")
    private EntityManager entityManager;
}
```

Create a model class (for example):

```
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

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public static enum Sex {
        MALE, FEMALE
    }
}
```

Create factory class:

```
@Factory(User.class)
public class UserFactory {
    // Simple value
    public String firstName = "John";

    public String lastName = "Doe";

    public Integer age = 18;

    // Lazy value
    public Function<User, Sex> sex = (user) -> user.getFirstName().equals("Jane") ? Sex.FEMALE : Sex.MALE;
}
```

Inject `Hairspray` instance into test class.

```
public class ExampleTest {
    @Inject
    private Hairspray factory;

    @Test
    public void test() {
        User user = factory.create(User.class);
        assertEquals(user.getFullName(), "John Doe");
        assertEquals(user.getSex(), Sex.MALE);

        // overwrite first name
        user = factory.create(User.class, (u) -> {
            u.setFirstName("Jane");
        });
        assertEquals(user.getFullName(), "Jane Doe");
        assertEquals(user.getSex(), Sex.FEMALE);
    }
}
```

## and more

see [https://github.com/wiro34/hairspray-example](hairspray-example)

## TODO

[] Sequence Number Generator
[] Association Support
