package yapily.marvel.explorer.backend.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * TODO use mapping framework
 */
@Component
public class CharacterMapper {

    public yapily.marvel.explorer.model.CharacterDataContainer map(yapily.marvel.rest.client.model.response.CharacterDataContainer source) {
        if (source != null) {
            yapily.marvel.explorer.model.CharacterDataContainer target = new yapily.marvel.explorer.model.CharacterDataContainer();
            target.setLimit(source.getLimit());
            target.setOffset(source.getOffset());
            target.setTotal(source.getTotal());

            if (source.getResults() != null) {
                List<yapily.marvel.explorer.model.MarvelCharacter> characterTargetList = new LinkedList<>();
                for (yapily.marvel.rest.client.model.response.MarvelCharacter characterSource : source.getResults()) {
                    characterTargetList.add(map(characterSource));
                }
                target.setResults(characterTargetList);
            }
            return target;
        } else {
            return null;
        }
    }

    public yapily.marvel.explorer.model.MarvelCharacter map(yapily.marvel.rest.client.model.response.MarvelCharacter source) {
        yapily.marvel.explorer.model.MarvelCharacter target = new yapily.marvel.explorer.model.MarvelCharacter();
        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setThumbnail(map(source.getThumbnail()));
        target.setStories(map(source.getStories()));
        target.setEvents(map(source.getEvents()));
        target.setSeries(map(source.getSeries()));
        return target;
    }

    public yapily.marvel.explorer.model.Image map(yapily.marvel.rest.client.model.response.Image source) {
        if (source != null) {
            yapily.marvel.explorer.model.Image target = new yapily.marvel.explorer.model.Image();
            target.setPath(source.getPath());
            target.setExtension(source.getExtension());
            return target;
        } else {
            return null;
        }
    }

    public yapily.marvel.explorer.model.StoryList map(yapily.marvel.rest.client.model.response.StoryList sourceList) {
        if (sourceList != null) {
            yapily.marvel.explorer.model.StoryList target = new yapily.marvel.explorer.model.StoryList();

            if (sourceList.getItems() != null) {
                List<yapily.marvel.explorer.model.StorySummary> targetList = new LinkedList<yapily.marvel.explorer.model.StorySummary>();
                for (yapily.marvel.rest.client.model.response.StorySummary source : sourceList.getItems()) {
                    targetList.add(map(source));
                }
                target.setItems(targetList);
            }

            return target;
        } else {
            return null;
        }
    }

    public yapily.marvel.explorer.model.StorySummary map(yapily.marvel.rest.client.model.response.StorySummary source) {
        if (source != null) {
            yapily.marvel.explorer.model.StorySummary target = new yapily.marvel.explorer.model.StorySummary();
            target.setName(source.getName());
            return target;
        } else {
            return null;
        }
    }

    public yapily.marvel.explorer.model.EventList map(yapily.marvel.rest.client.model.response.EventList sourceList) {
        if (sourceList != null) {
            yapily.marvel.explorer.model.EventList target = new yapily.marvel.explorer.model.EventList();

            if (sourceList.getItems() != null) {
                List<yapily.marvel.explorer.model.EventSummary> targetList = new LinkedList<yapily.marvel.explorer.model.EventSummary>();
                for (yapily.marvel.rest.client.model.response.EventSummary source : sourceList.getItems()) {
                    targetList.add(map(source));
                }
                target.setItems(targetList);
            }

            return target;
        } else {
            return null;
        }
    }

    public yapily.marvel.explorer.model.EventSummary map(yapily.marvel.rest.client.model.response.EventSummary source) {
        if (source != null) {
            yapily.marvel.explorer.model.EventSummary target = new yapily.marvel.explorer.model.EventSummary();
            target.setName(source.getName());
            return target;
        } else {
            return null;
        }
    }

    public yapily.marvel.explorer.model.SeriesList map(yapily.marvel.rest.client.model.response.SeriesList sourceList) {
        if (sourceList != null) {
            yapily.marvel.explorer.model.SeriesList target = new yapily.marvel.explorer.model.SeriesList();

            if (sourceList.getItems() != null) {
                List<yapily.marvel.explorer.model.SeriesSummary> targetList = new LinkedList<yapily.marvel.explorer.model.SeriesSummary>();
                for (yapily.marvel.rest.client.model.response.SeriesSummary source : sourceList.getItems()) {
                    targetList.add(map(source));
                }
                target.setItems(targetList);
            }

            return target;
        } else {
            return null;
        }
    }

    public yapily.marvel.explorer.model.SeriesSummary map(yapily.marvel.rest.client.model.response.SeriesSummary source) {
        if (source != null) {
            yapily.marvel.explorer.model.SeriesSummary target = new yapily.marvel.explorer.model.SeriesSummary();
            target.setName(source.getName());
            return target;
        } else {
            return null;
        }
    }

}
