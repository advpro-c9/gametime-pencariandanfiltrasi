package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchFilterRepositoryTest {

    @Mock
    private SearchFilterRepository searchFilterRepository;

    @InjectMocks
    private Game game;
    private final UUID id = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        game = new Game();
        game.setGameId(id);
        game.setGameName("Test Game");
        game.setGameDescription("Test Game Description");
        game.setGameGenre("Adventure");
        game.setGamePrice(49.99);
    }

    @Test
    void testFindByGameNameContaining() {
        when(searchFilterRepository.findByGameNameContaining(anyString())).thenReturn(Arrays.asList(game));
        List<Game> foundGames = searchFilterRepository.findByGameNameContaining("Test");

        assertNotNull(foundGames);
        assertEquals(1, foundGames.size());
        assertEquals(game.getGameName(), foundGames.get(0).getGameName());

        verify(searchFilterRepository, times(1)).findByGameNameContaining("Test");
    }

    @Test
    void testFindByGameGenreContaining() {
        when(searchFilterRepository.findByGameGenreContaining(anyString())).thenReturn(Arrays.asList(game));
        List<Game> foundGames = searchFilterRepository.findByGameGenreContaining("Adventure");

        assertNotNull(foundGames);
        assertEquals(1, foundGames.size());
        assertEquals(game.getGameGenre(), foundGames.get(0).getGameGenre());

        verify(searchFilterRepository, times(1)).findByGameGenreContaining("Adventure");
    }

    @Test
    void testFindByGamePriceBetween() {
        when(searchFilterRepository.findByGamePriceBetween(anyDouble(), anyDouble())).thenReturn(Arrays.asList(game));
        List<Game> foundGames = searchFilterRepository.findByGamePriceBetween(30.00, 60.00);

        assertNotNull(foundGames);
        assertEquals(1, foundGames.size());
        assertEquals(game.getGamePrice(), foundGames.get(0).getGamePrice());

        verify(searchFilterRepository, times(1)).findByGamePriceBetween(30.00, 60.00);
    }

    @Test
    void testFindByGameNameContainingNoResult() {
        when(searchFilterRepository.findByGameNameContaining(anyString())).thenReturn(Collections.emptyList());
        List<Game> foundGames = searchFilterRepository.findByGameNameContaining("NonExistingGame");
        assertTrue(foundGames.isEmpty(), "The list of found games should be empty");
        verify(searchFilterRepository, times(1)).findByGameNameContaining("NonExistingGame");
    }

    @Test
    void testFindByGameGenreContainingNoResult() {
        when(searchFilterRepository.findByGameGenreContaining(anyString())).thenReturn(Collections.emptyList());
        List<Game> foundGames = searchFilterRepository.findByGameGenreContaining("NonExistingGenre");
        assertTrue(foundGames.isEmpty(), "The list of found games should be empty");
        verify(searchFilterRepository, times(1)).findByGameGenreContaining("NonExistingGenre");
    }

    @Test
    void testFindByGamePriceBetweenNoResult() {
        when(searchFilterRepository.findByGamePriceBetween(anyDouble(), anyDouble())).thenReturn(Collections.emptyList());
        List<Game> foundGames = searchFilterRepository.findByGamePriceBetween(100.00, 200.00);
        assertTrue(foundGames.isEmpty(), "The list of found games should be empty");
        verify(searchFilterRepository, times(1)).findByGamePriceBetween(100.00, 200.00);
    }

    @Test
    void testFindByGameNameContainingException() {
        when(searchFilterRepository.findByGameNameContaining(anyString())).thenThrow(new RuntimeException("Database error"));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            searchFilterRepository.findByGameNameContaining("Test");
        });
        assertEquals("Database error", exception.getMessage());
        verify(searchFilterRepository, times(1)).findByGameNameContaining("Test");
    }

    @Test
    void testFindByGameGenreContainingException() {
        when(searchFilterRepository.findByGameGenreContaining(anyString())).thenThrow(new RuntimeException("Database error"));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            searchFilterRepository.findByGameGenreContaining("Adventure");
        });
        assertEquals("Database error", exception.getMessage());
        verify(searchFilterRepository, times(1)).findByGameGenreContaining("Adventure");
    }

    @Test
    void testFindByGamePriceBetweenException() {
        when(searchFilterRepository.findByGamePriceBetween(anyDouble(), anyDouble())).thenThrow(new RuntimeException("Database error"));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            searchFilterRepository.findByGamePriceBetween(30.00, 60.00);
        });
        assertEquals("Database error", exception.getMessage());
        verify(searchFilterRepository, times(1)).findByGamePriceBetween(30.00, 60.00);
    }

}
