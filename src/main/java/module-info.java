module geographyquiz {
	
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.media;
	
	requires org.apache.commons.ios;
	requires org.controlsfx.controls;
	requires it.sauronsoftware.juniques;
	requires org.reactfxs;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;
	
	exports com.johnzaro.geographyquiz.core;
	exports com.johnzaro.geographyquiz.dataStructures;
	exports com.johnzaro.geographyquiz.screens;
}