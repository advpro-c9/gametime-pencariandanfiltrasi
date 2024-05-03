package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.repository.SearchFilterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SearchFilterServiceImplTest {

    @Mock
    SearchFilterRepository searchFilterRepository;

    @InjectMocks
    SearchFilterServiceImpl searchFilterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Captor
    ArgumentCaptor<Game> productCaptor;

    @Test
    void create() {
        Game game = new Game();
        game.setGameId("1");
        game.setGameName("Game A");
        game.setGameDescription("Desc Game A");
        game.setGamePrice(20.0);
        game.setGameGenres(Arrays.asList("Action", "Adventure"));
        when(searchFilterRepository.create(game)).thenReturn(game);

        Game createdGame = searchFilterService.create(game);
        assertEquals(game, createdGame);
        verify(searchFilterRepository, times(1)).create(game);
    }

    @Test
    void findAll() {
        List<Game> games = new ArrayList<>();

        Game game1 = new Game();
        game1.setGameId("1");
        game1.setGameName("Game A");
        game1.setGameDescription("Desc Game A");
        game1.setGamePrice(20.0);
        game1.setGameGenres(Arrays.asList("Action", "Adventure"));
        games.add(game1);

        Game game2 = new Game();
        game2.setGameId("2");
        game2.setGameName("Game B");
        game2.setGameDescription("Desc Game B");
        game2.setGamePrice(30.0);
        game2.setGameGenres(Arrays.asList("Strategy", "Action"));
        games.add(game2);

        when(searchFilterRepository.findAll()).thenReturn(games.iterator());

        List<Game> allGames = searchFilterService.findAll();

        assertEquals(games.size(), allGames.size());
        assertTrue(allGames.contains(game1) && allGames.contains(game2));
        verify(searchFilterRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        Game game = new Game();
        game.setGameId("1");
        game.setGameName("Game A");
        game.setGameDescription("Desc Game A");
        game.setGamePrice(20.0);
        game.setGameGenres(Arrays.asList("Action", "Adventure"));
        when(searchFilterRepository.findById(game.getGameId())).thenReturn(game);

        Game foundGame = searchFilterService.findById(game.getGameId());

        assertEquals(game, foundGame);
        verify(searchFilterRepository, times(1)).findById(game.getGameId());
    }

    @Test
    void findByName() {
        List<Game> games = new ArrayList<>();

        Game game1 = new Game();
        game1.setGameId("1");
        game1.setGameName("Pokemon A");
        game1.setGameDescription("Desc Game A");
        game1.setGamePrice(20.0);
        game1.setGameGenres(Arrays.asList("Action", "Adventure"));
        games.add(game1);

        Game game2 = new Game();
        game2.setGameId("2");
        game2.setGameName("Pokemon B");
        game2.setGameDescription("Desc Game B");
        game2.setGamePrice(30.0);
        game2.setGameGenres(Arrays.asList("Strategy", "Action"));
        games.add(game2);
        when(searchFilterRepository.findByName("Pokemon")).thenReturn(games);

        List <Game> foundGames = searchFilterService.findByName("Pokemon");

        assertEquals(2, foundGames.size());
        assertEquals(games.getFirst().getGameName(), foundGames.getFirst().getGameName());
        assertEquals(games.get(1).getGameName(), foundGames.get(1).getGameName());
        verify(searchFilterRepository, times(1)).findByName("Pokemon");
    }

    @Test
    void findByPriceRange() {
        List<Game> gamesInRange = new ArrayList<>();

        Game game1 = new Game();
        game1.setGameId("1");
        game1.setGameName("Game A");
        game1.setGameDescription("Desc Game A");
        game1.setGamePrice(20.0);  // This game is within the range
        game1.setGameGenres(Arrays.asList("Action", "Adventure"));
        gamesInRange.add(game1);

        Game game2 = new Game();
        game2.setGameId("2");
        game2.setGameName("Game B");
        game2.setGameDescription("Desc Game B");
        game2.setGamePrice(30.0);  // This game is outside the range
        game2.setGameGenres(Arrays.asList("Strategy", "Action"));

        when(searchFilterRepository.findByPriceRange(0.0, 25.0)).thenReturn(gamesInRange);

        List<Game> foundGames = searchFilterService.findByPriceRange(0.0, 25.0);

        assertEquals(1, foundGames.size());
        assertEquals(game1, foundGames.getFirst());
        verify(searchFilterRepository, times(1)).findByPriceRange(0.0, 25.0);
    }


    @Test
    void findByGenres() {
        List<Game> games = new ArrayList<>();

        Game game1 = new Game();
        game1.setGameId("1");
        game1.setGameName("Pokemon A");
        game1.setGameDescription("Desc Game A");
        game1.setGamePrice(20.0);
        game1.setGameGenres(Arrays.asList("Action", "Adventure"));
        games.add(game1);

        Game game2 = new Game();
        game2.setGameId("2");
        game2.setGameName("Pokemon B");
        game2.setGameDescription("Desc Game B");
        game2.setGamePrice(30.0);
        game2.setGameGenres(Arrays.asList("Strategy", "Action"));
        games.add(game2);

        List<String> genres = Arrays.asList("Action", "Adventure");
        when(searchFilterRepository.findByGenres(genres)).thenReturn(games);

        List<Game> foundGames = searchFilterService.findByGenres(genres);

        assertEquals(games.size(), foundGames.size());
        assertEquals(games.getFirst().getGameGenres(), foundGames.getFirst().getGameGenres());
        assertEquals(games.get(1).getGameGenres(), foundGames.get(1).getGameGenres());
        verify(searchFilterRepository, times(1)).findByGenres(genres);
    }
}
