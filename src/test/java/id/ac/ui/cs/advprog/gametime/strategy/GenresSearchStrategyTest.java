package id.ac.ui.cs.advprog.gametime.strategy;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.repository.SearchFilterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenresSearchStrategyTest {

    @Mock
    private SearchFilterRepository searchFilterRepository;

    private GenresSearchStrategy genresSearchStrategy;

    private Game game;
    private final UUID id = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        genresSearchStrategy = new GenresSearchStrategy(searchFilterRepository);

        game = new Game();
        game.setGameId(id);
        game.setGameName("Test Game");
        game.setGameDescription("Test Game Description");
        game.setGameGenre("Adventure");
        game.setGamePrice(49.99);
    }

    @Test
    void testSearch() throws ExecutionException, InterruptedException {
        String genre = "Adventure";

        when(searchFilterRepository.findByGameGenreContaining(genre)).thenReturn(Arrays.asList(game));

        CompletableFuture<List<Game>> foundGamesFuture = genresSearchStrategy.search(genre);
        List<Game> foundGames = foundGamesFuture.get();

        assertNotNull(foundGames);
        assertEquals(1, foundGames.size());
        assertEquals(game, foundGames.get(0));

        verify(searchFilterRepository, times(1)).findByGameGenreContaining(genre);
    }

    @Test
    void testSearchNoResult() throws ExecutionException, InterruptedException {
        String genre = "NonExistingGenre";

        when(searchFilterRepository.findByGameGenreContaining(genre)).thenReturn(Collections.emptyList());

        CompletableFuture<List<Game>> foundGamesFuture = genresSearchStrategy.search(genre);
        List<Game> foundGames = foundGamesFuture.get();

        assertNotNull(foundGames);
        assertTrue(foundGames.isEmpty());

        verify(searchFilterRepository, times(1)).findByGameGenreContaining(genre);
    }

    @Test
    void testSearchException() {
        String genre = "Adventure";

        when(searchFilterRepository.findByGameGenreContaining(genre)).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            CompletableFuture<List<Game>> foundGamesFuture = genresSearchStrategy.search(genre);
            foundGamesFuture.join();
        });

        assertEquals("Database error", exception.getMessage());

        verify(searchFilterRepository, times(1)).findByGameGenreContaining(genre);
    }
}
