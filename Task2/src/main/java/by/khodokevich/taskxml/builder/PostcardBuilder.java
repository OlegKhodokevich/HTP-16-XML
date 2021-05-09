package by.khodokevich.taskxml.builder;

import by.khodokevich.taskxml.entity.Postcard;
import by.khodokevich.taskxml.exception.ProjectXMLException;

import java.util.Set;

public interface PostcardBuilder {
    Set<Postcard> createSetPostcardsFromXML(String fileNameXML) throws ProjectXMLException;
}
