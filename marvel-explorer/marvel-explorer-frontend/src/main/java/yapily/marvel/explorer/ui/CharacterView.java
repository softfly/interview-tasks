package yapily.marvel.explorer.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import yapily.marvel.explorer.backend.CharactersService;
import yapily.marvel.explorer.model.EventSummary;
import yapily.marvel.explorer.model.MarvelCharacter;
import yapily.marvel.explorer.model.SeriesSummary;
import yapily.marvel.explorer.model.StorySummary;

@org.springframework.stereotype.Component
public class CharacterView extends VerticalLayout {

    protected CharactersService charactersService;

	//protected MainView mainView;

	protected MarvelCharacter character;

	protected Label nameLabel = new Label("Name:");

	protected H1 name = new H1();

	protected Image thumbnail = new Image();

	protected Label descriptionLabel = new Label("Description:");

	protected Paragraph description = new Paragraph();

	protected Tab storiesTab = new Tab("Stories");

	protected Tab eventsTab = new Tab("Events");

	protected Tab seriesTab = new Tab("Series");

    //https://vaadin.com/components/vaadin-tabs/java-examples
	protected Tabs tabs = new Tabs(storiesTab, eventsTab, seriesTab);

	protected ListBox<String> storiesPage = initStoriesPage();

	protected ListBox<String> eventsPage = initEventsPage();

	protected ListBox<String> seriesPage = initSeriesPage();

	protected Div pages = new Div(storiesPage, eventsPage, seriesPage);

	protected Map<Tab, Component> tabsToPages = initTabsToPages();

	protected Set<Component> pagesShown = Stream.of(storiesPage).collect(Collectors.toSet());

	protected Div tabsPages = initTabsPages();

    public CharacterView(@Autowired CharactersService charactersService) {
    	this.charactersService = charactersService;
        onTabClick();
        initLayout();
        setCharacter(null);
    }

    protected void initLayout() {
    	setClassName("characterLayout");

        FlexLayout hl1 = new FlexLayout();
        hl1.setClassName("characterInnerLayout");
        Div thumbnailDiv = new Div();
        thumbnailDiv.add(thumbnail);
        thumbnailDiv.setClassName("thumbnail");

        VerticalLayout vl1 = new VerticalLayout();
        vl1.setClassName("characterRightLayout");
        vl1.add(descriptionLabel, description, tabsPages);
        vl1.setSizeUndefined();

        hl1.add(thumbnailDiv, vl1);

        add(nameLabel, name, hl1);
        setHeightFull();
    }

	protected ListBox<String> initStoriesPage() {
		ListBox<String> page = new ListBox<>();
		page.setVisible(true);
		return page;
	}

	protected ListBox<String> initEventsPage() {
		ListBox<String> page = new ListBox<>();
		page.setVisible(false);
		return page;
	}

	protected ListBox<String> initSeriesPage() {
		ListBox<String> page = new ListBox<>();
		page.setVisible(false);
		return page;
	}

	protected Map<Tab, Component> initTabsToPages() {
		Map<Tab, Component> tabsToPages = new HashMap<>();
		tabsToPages.put(storiesTab, storiesPage);
        tabsToPages.put(eventsTab, eventsPage);
        tabsToPages.put(seriesTab, seriesPage);
        return tabsToPages;
	}

	protected Div initTabsPages() {
		Div div = new Div();
		div.setWidth("100%");
		div.add(tabs);
		div.add(pages);
        return div;
	}

    protected void onTabClick() {
        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();

            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
    }

    public MarvelCharacter getCharacter() {
        return character;
    }

    public void setCharacter(Long id) {
    	if (id != null) {
			try {
				Optional<MarvelCharacter> response = charactersService.getCharacter(id);
		        if (response.isPresent()) {
		        	MarvelCharacter character = response.get();
		            setVisible(true);
		            updateThumbnail(character);
		            updateDescription(character);
		            updateStories(character);
		            updateEvents(character);
		            updateSeries(character);
		            name.setText(character.getName());
		            //mainView.setSizeFull();
		            return;
		        }
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	setVisible(false);
    }

    protected void updateDescription(MarvelCharacter character) {
        if (StringUtils.isEmpty(character.getDescription())) {
            descriptionLabel.setVisible(false);
            description.setVisible(false);
        } else {
            descriptionLabel.setVisible(true);
            description.setVisible(true);
        }
        description.setText(character.getDescription());
    }

    protected void updateStories(MarvelCharacter character) {
        if (character.getStories() != null) {
            List<StorySummary> list = character.getStories().getItems();
            if (list != null && list.size() > 0) {
                storiesTab.setEnabled(true);
                storiesPage.setItems(list.stream().map(e -> e.getName()));
                return;
            }
        }

        storiesTab.setEnabled(false);
        storiesPage.removeAll();
    }

    protected void updateEvents(MarvelCharacter character) {
        if (character.getEvents() != null) {
            List<EventSummary> list = character.getEvents().getItems();
            if (list != null && list.size() > 0) {
                eventsTab.setEnabled(true);
                eventsPage.setItems(list.stream().map(e -> e.getName()));
                return;
            }
        }

        eventsTab.setEnabled(false);
        eventsPage.removeAll();
    }

    protected void updateSeries(MarvelCharacter character) {
        if (character.getSeries() != null) {
            List<SeriesSummary> list = character.getSeries().getItems();
            if (list != null && list.size() > 0) {
                seriesTab.setEnabled(true);
                seriesPage.setItems(list.stream().map(e -> e.getName()));
                return;
            }
        }

        seriesTab.setEnabled(false);
        seriesPage.removeAll();
    }

    protected void updateThumbnail(MarvelCharacter character) {
        thumbnail.setSrc(new StringBuilder(character.getThumbnail().getPath()).append(".").append(character.getThumbnail().getExtension()).toString());
    }
}
