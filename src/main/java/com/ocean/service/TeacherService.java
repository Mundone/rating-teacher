package com.ocean.service;

import com.ocean.domain.Teacher;
import com.ocean.repository.TeacherRepository;
import com.ocean.service.dto.TeacherDTO;
import com.ocean.service.mapper.TeacherMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Teacher}.
 */
@Service
@Transactional
public class TeacherService {

    private final Logger log = LoggerFactory.getLogger(TeacherService.class);

    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    /**
     * Save a teacher.
     *
     * @param teacherDTO the entity to save.
     * @return the persisted entity.
     */
    public TeacherDTO save(TeacherDTO teacherDTO) {
        log.debug("Request to save Teacher : {}", teacherDTO);
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        teacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    /**
     * Update a teacher.
     *
     * @param teacherDTO the entity to save.
     * @return the persisted entity.
     */
    public TeacherDTO update(TeacherDTO teacherDTO) {
        log.debug("Request to update Teacher : {}", teacherDTO);
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        teacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    /**
     * Partially update a teacher.
     *
     * @param teacherDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TeacherDTO> partialUpdate(TeacherDTO teacherDTO) {
        log.debug("Request to partially update Teacher : {}", teacherDTO);

        return teacherRepository
            .findById(teacherDTO.getId())
            .map(existingTeacher -> {
                teacherMapper.partialUpdate(existingTeacher, teacherDTO);

                return existingTeacher;
            })
            .map(teacherRepository::save)
            .map(teacherMapper::toDto);
    }

    /**
     * Get all the teachers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TeacherDTO> findAll() {
        log.debug("Request to get all Teachers");
        return teacherRepository.findAll().stream().map(teacherMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one teacher by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TeacherDTO> findOne(Long id) {
        log.debug("Request to get Teacher : {}", id);
        return teacherRepository.findById(id).map(teacherMapper::toDto);
    }

    /**
     * Delete the teacher by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Teacher : {}", id);
        teacherRepository.deleteById(id);
    }
}
