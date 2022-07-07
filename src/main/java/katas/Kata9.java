package katas;

import model.MovieList;
import util.DataUtil;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
    Goal: Retrieve each video's id, title, middle interesting moment time, and smallest box art url
    DataSource: DataUtil.getMovies()
    Output: List of ImmutableMap.of("id", 5, "title", "some title", "time", new Date(), "url", "someUrl")
*/
public class Kata9 {
    public static List<Map> execute() {
        List<MovieList> movieLists = DataUtil.getMovieLists();

        return movieLists.stream()
                .flatMap(movieList -> movieList.getVideos().stream())
                .map(movie -> Map.of(
                                "id", movie.getId(), "title", movie.getTitle(),
                                "time", movie.getInterestingMoments().stream()
                                        .flatMap(interestingMoment -> Stream.of(interestingMoment.getTime()))
                                        .mapToLong(Date::getTime)
                                        .average()
                                        .stream().mapToObj(prom -> new Date((long) prom)),
                                "boxart", movie.getBoxarts()
                                        .stream()
                                        .min(Comparator.comparing(boxArt -> boxArt.getUrl().length()))
                                        .orElseThrow().getUrl()
                        )
                )
                .collect(Collectors.toList());
    }
}
