package org.example.factory_core_manager.dto;
import lombok.Getter;
import lombok.Setter;
import org.example.factory_core_manager.enumeration.WorkerTransActionType;

@Getter
@Setter
public class TransActionSave {

    private String description;

    private WorkerTransActionType type;

    private float amount ;

}
