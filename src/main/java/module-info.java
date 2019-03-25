module geographyquiz {
	
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.media;
	
	requires org.apache.commons.ios;
	requires org.controlsfx.controls;
	requires it.sauronsoftware.juniques;
	requires org.jdom2s;
	requires org.reactfxs;
	
	exports com.johnzaro.geographyquiz.core;
	exports com.johnzaro.geographyquiz.dataStructures;
	exports com.johnzaro.geographyquiz.screens;
}