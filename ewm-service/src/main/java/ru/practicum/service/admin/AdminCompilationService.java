package ru.practicum.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.compilation.CompilationInDto;
import ru.practicum.dto.compilation.CompilationOutDto;
import ru.practicum.dto.compilation.CompilationUpdateInDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.EventMapperSupport;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.repository.EventRepository;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCompilationService {

    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;
    private final EventMapperSupport eventMapperSupport;

    @Transactional
    public CompilationOutDto createCompilation(CompilationInDto dto) {
        List<Event> events = dto.getEvents() != null
                ? eventRepository.findAllById(dto.getEvents())
                : List.of();

        Compilation compilation = new Compilation();
        compilation.setTitle(dto.getTitle());
        compilation.setPinned(dto.getPinned());
        compilation.getEvents().addAll(events);
        compilation = compilationRepository.save(compilation);

        return eventMapperSupport.mapCompilation(compilation);
    }

    @Transactional
    public void deleteCompilation(long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found"));
        compilationRepository.delete(compilation);
    }

    @Transactional
    public CompilationOutDto updateCompilation(@Valid CompilationUpdateInDto dto, long compId) {
        if (dto.getTitle() != null && dto.getTitle().isBlank()) {
            throw new ValidationException("Title is blank");
        }

        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found"));

        if (dto.getPinned() != null) {
            compilation.setPinned(dto.getPinned());
        }
        if (dto.getTitle() != null) {
            compilation.setTitle(dto.getTitle());
        }
        if (dto.getEvents() != null) {
            List<Event> newEvents = eventRepository.findAllById(dto.getEvents());
            compilation.getEvents().addAll(newEvents);
        }
        return eventMapperSupport.mapCompilation(compilation);
    }
}
