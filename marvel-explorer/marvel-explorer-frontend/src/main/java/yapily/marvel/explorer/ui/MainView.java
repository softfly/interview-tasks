package yapily.marvel.explorer.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import yapily.marvel.explorer.backend.CharactersSearchParams;
import yapily.marvel.explorer.backend.CharactersService;
import yapily.marvel.explorer.model.MarvelCharacter;

@Route
@HtmlImport("frontend://styles/shared-styles.html")
public class MainView extends FlexLayout {

    @Autowired
    protected CharactersService charactersService;

    @Autowired
    protected CharactersSearchParams searchParams;

    protected CharacterView characterView;

    protected DataProvider<MarvelCharacter, Void> dataProvider = initDataProvider();

    protected Grid<MarvelCharacter> grid = initGrid();

    protected TextField searchField = initSearchField();

    public MainView(@Autowired CharacterView characterView) throws Exception {
    	this.characterView = characterView;
        setClassName("mainLayout");

        VerticalLayout vl = new VerticalLayout();
        vl.setClassName("charactersLayout");
        vl.add(searchField, grid);

        add(vl, characterView);
        setSizeFull();
    }

    protected TextField initSearchField() {
        TextField searchField = new TextField();
        searchField.setPlaceholder("Filter by name...");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> {
            searchParams.setNameStartsWith(e.getValue());
            grid.getDataProvider().refreshAll();
        });
        return searchField;
    }

    /**
     * https://vaadin.com/docs/v13/flow/binding-data/tutorial-flow-data-provider.html
     */
    protected DataProvider<MarvelCharacter, Void> initDataProvider() {
        DataProvider<MarvelCharacter, Void> dataProvider = DataProvider.fromCallbacks(
                query -> {
                    searchParams.setLimit(query.getLimit());
                    searchParams.setOffset(query.getOffset());
                    try {
                        List<MarvelCharacter> list = getCharactersService().getCharacters(searchParams);
                        displayFirstCharacter(list);
                        return list.stream();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                },
                query -> {
                    searchParams.setLimit(query.getLimit());
                    searchParams.setOffset(query.getOffset());
                    try {
                        Integer c = getCharactersService().countCharacters(searchParams);
                        return c != null ? c : 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }
                });
        return dataProvider;
    }

    protected void displayFirstCharacter(List<MarvelCharacter> list) {
        if (characterView.getCharacter() == null && list.get(0) != null) {
            characterView.setCharacter(list.get(0).getId());
        }
    }

    protected Grid<MarvelCharacter> initGrid() {
        Grid<MarvelCharacter> grid = new Grid<>(MarvelCharacter.class);
        initDataProvider();
        grid.setColumns("name");
        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(event -> characterView.setCharacter(grid.asSingleSelect().getValue().getId()));
        grid.setDataProvider(dataProvider);
        return grid;
    }

    protected CharactersService getCharactersService() {
        return charactersService;
    }

    protected void setCharactersService(CharactersService charactersService) {
        this.charactersService = charactersService;
    }

}
