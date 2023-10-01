package ru.practicum.service.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.compilation.CompilationOutDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.EventMapperSupport;
import ru.practicum.model.Compilation;
import ru.practicum.repository.CompilationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonCompilationService {

    private final CompilationRepository compilationRepository;
    private final EventMapperSupport eventMapperSupport;

    @Transactional(readOnly = true)
    public CompilationOutDto getCompilation(long compilationId) {
        Compilation compilation = compilationRepository.findById(compilationId)
                .orElseThrow(() -> new NotFoundException("Compilation "));
        return eventMapperSupport.mapCompilation(compilation);
    }

    @Transactional(readOnly = true)
    public List<CompilationOutDto> getCompilations(@Nullable Boolean pinned, int from, int size) {
        List<Compilation> compilations;
        if (pinned == null) {
            compilations = compilationRepository.findAll(PageRequest.of(from / size, size, Sort.by("id"))).getContent();
        } else {
            compilations = compilationRepository.findByPinned(pinned, PageRequest.of(from / size, size, Sort.by("id"))).getContent();
        }
        return compilations.stream()
                .map(eventMapperSupport::mapCompilation)
                .collect(Collectors.toUnmodifiableList());
    }
}
