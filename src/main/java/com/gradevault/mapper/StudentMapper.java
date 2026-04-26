package com.gradevault.mapper;

import com.gradevault.dto.StudentRequestDTO;
import com.gradevault.dto.StudentResponseDTO;
import com.gradevault.entity.Student;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    
    StudentResponseDTO toResponseDTO(Student student);
    
    Student toEntity(StudentRequestDTO requestDTO);
    
    List<StudentResponseDTO> toResponseDTOList(List<Student> students);
}
