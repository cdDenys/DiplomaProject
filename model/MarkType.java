package adaptiveschool.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "mark_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarkType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mark_type")
    private String markType;

    @Size(max=500)
    private String description;

    @NotNull
    @Column(name = "is_active")
    boolean isActive;
}
