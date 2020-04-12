package user;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        User user = User.builder()
                .id(1L)
                .name("James Bond")
                .username("007")
                .password("jamesbond007")
                .email("jb007@example.com")
                .gender(User.Gender.MALE)
                .dob(LocalDate.parse("1977-01-01"))
                .enabled(true)
                .build();

        User user2 = User.builder()
                .id(23L)
                .name("James Bond")
                .username("010")
                .password("jamesbond010")
                .email("jb010@example.com")
                .gender(User.Gender.FEMALE)
                .dob(LocalDate.parse("1977-01-01"))
                .enabled(false)
                .build();

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);
            dao.createTable();

            dao.insert(user);
            dao.insert(user2);

            dao.findById(1).stream().forEach(System.out::println);

            dao.findByUserName("010").stream().forEach(System.out::println);

            dao.list().stream().forEach(System.out::println);

            dao.delete(user);
            dao.list().stream().forEach(System.out::println);
        }
    }
}
