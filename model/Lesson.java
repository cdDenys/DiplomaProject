package adaptiveschool.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lesson")
@Data
@EqualsAndHashCode(exclude = {"marks", "subject", "clazz", "file"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Positive
    @Column(name = "lesson_number")
    private byte lessonNumber;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Future
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd", timezone="Europe/Kiev")
    private Date date;

    @Size(max = 500)
    private String hometask;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "mark_type_id")
    private MarkType markType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homework_file_id")
    private File file;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotNull
    private Clazz clazz;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotNull
    private Subject subject;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "lesson")
    private final Set<@NotNull Mark> marks = new HashSet<>();

    public Lesson(byte lessonNumber, Date date, String hometask, MarkType markType, File file, Clazz clazz,
            Subject subject) {
        super();
        this.lessonNumber = lessonNumber;
        this.date = date;
        this.hometask = hometask;
        this.markType = markType;
        this.file = file;
        this.clazz = clazz;
        this.subject = subject;
    }
}
