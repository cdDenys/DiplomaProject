package adaptiveschool.dto;

import adaptiveschool.model.Clazz;
import io.swagger.annotations.ApiModel;
import lombok.*;

@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassDTO {

    private int id;

    private int classYear;

    private String className;

    private String classDescription;

    private boolean isActive;

    private int numOfStudents;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}