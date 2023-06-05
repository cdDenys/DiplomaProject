package adaptiveschool.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarkDataPointDTO{

    private double y;
    
    private LocalDate x;
    
    private int weight;
}
