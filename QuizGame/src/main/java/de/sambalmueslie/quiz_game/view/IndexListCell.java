package de.sambalmueslie.quiz_game.view;

import de.sambalmueslie.quiz_game.data.Index;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

public class IndexListCell extends ListCell<Index> {

	@Override
	protected void updateItem(final Index item, final boolean empty) {
		super.updateItem(item, empty);
		if (item == null) return;
		setTextAlignment(TextAlignment.LEFT);
		setText(String.format("%02d   € %d", item.getNumber(), item.getMoney()));
		if (item.isSafe()) {
			setTextFill(Paint.valueOf("white"));
		} else {
			setTextFill(Paint.valueOf("#FFCC00"));
		}
	}
}
