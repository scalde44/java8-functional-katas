package katas;

import model.MovieList;
import util.DataUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    Goal: Retrieve the id, title, and smallest box art url for every video
    DataSource: DataUtil.getMovieLists()
    Output: List of ImmutableMap.of("id", "5", "title", "Bad Boys", "boxart": "url)
*/
public class Kata7 {
    public static List<Map> execute() {
        List<MovieList> movieLists = DataUtil.getMovieLists();

        return movieLists.stream()
                .flatMap(movieList -> movieList.getVideos().stream())
                .map(movie -> Map.of(
                                "id", movie.getId(), "title", movie.getTitle(),
                                "boxart", movie.getBoxarts().stream().min(Comparator.comparing(boxArt -> boxArt.getUrl().length())).orElseThrow().getUrl()
                        )
                )
                .collect(Collectors.toList());
    }
}
