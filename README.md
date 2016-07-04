Hairspray
=========

Hairspray is a fixture library for testing like factory_girl.
The library can creating fixtures easily.

## Usage

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
@ApplicationScoped
@Factory(User.class)
public class UserFactory {
    public String firstName(User user) {
        return "John";
    }

    public String lastName(User user) {
        return "Doe";
    }

    public Integer age(User user) {
        return 18;
    }

    public Sex sex(User user) {
        return Sex.MALE;
    }
}
```

Inject `Hairspray` instance into test class.

```
public class UserTest {
    @Inject
    private Hairspray factory;

    private User user;

    @Test
    public void testGetFullName() {
        user = factory.create(User.class);
        assertEquals(user.getName(), "John Doe");
    }
}
```

## Customize factory

Depends on another field:

```
@ApplicationScoped
@Factory(User.class)
public class UserFactory {

    ...

    public Sex sex(User user) {
        if (user.getFirstName().equals("Jane")) {
            return Sex.FEMALE;
        } else {
            return Sex.MALE;
        }
    }
}
```

Create with another entity:

```
@ApplicationScoped
@Factory(Post.class)
public class PostFactory {

    @Inject
    private EntityManager entityManager;

    public User auther(Post post) {
        if (post.getUser() == null) {
            return entityManager.create( ... );
        } else {
            return post.getUser();
        }
    }
}
```
