package mate.academy.spring.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.spring.model.dto.MovieSessionRequestDto;
import mate.academy.spring.model.dto.MovieSessionResponseDto;
import mate.academy.spring.service.MovieSessionService;
import mate.academy.spring.service.mapper.MovieSessionDtoMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-sessions")
public class MovieSessionController {
    private final MovieSessionService movieSessionService;
    private final MovieSessionDtoMapper movieSessionDtoMapper;

    public MovieSessionController(MovieSessionService movieSessionService,
                                  MovieSessionDtoMapper movieSessionDtoMapper) {
        this.movieSessionService = movieSessionService;
        this.movieSessionDtoMapper = movieSessionDtoMapper;
    }
    
    @GetMapping("/available")
    public List<MovieSessionResponseDto> getAvailableMovieSessions(
            @RequestParam
            @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date,
            @RequestParam Long movieId) {
        return movieSessionService.findAvailableSessions(movieId, date)
                .stream()
                .map(movieSessionDtoMapper::parseToDto)
                .collect(Collectors.toList());
    }
    
    @DeleteMapping("/{id}")
    public MovieSessionResponseDto deleteMovieSession(
            @PathVariable Long id) {
        return movieSessionDtoMapper.parseToDto(
                movieSessionService.deleteById(id));
    }
    
    @PostMapping
    public MovieSessionResponseDto addMovieSession(
            @RequestBody MovieSessionRequestDto movieSessionRequestDto) {
        return movieSessionDtoMapper.parseToDto(
                movieSessionService.add(
                        movieSessionDtoMapper.parseToModel(movieSessionRequestDto)));
    }
    
    @PutMapping("/{id}")
    public MovieSessionResponseDto updateMovieSession(
            @PathVariable Long id,
            @RequestBody MovieSessionRequestDto movieSessionRequestDto) {
        return movieSessionDtoMapper.parseToDto(
                movieSessionService.update(
                        movieSessionDtoMapper.parseToModel(movieSessionRequestDto)));
    }
}
