package com.tokioschool.flightapp.store.configuration;

import com.tokioschool.flightapp.store.util.FileUtil;
import jakarta.annotation.Nonnull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "application.store")
public record StoreConfigurationProperties(Path absolutePath, String relativePath) {


    /**
     * Return the path of resources from relative path read in the properties
     * @return path of resource
     *
     * @SEE {@link FileUtil#getCurrentWorking(String)}
     */
    public Path buildResourcePathFromRelativePathGivenNameResource(){
        return FileUtil.getCurrentWorking(relativePath);
    }

    /**
     * Return the path of resources from relative path absolute in the properties
     * @return path of resource
     */
    public Path buildResourcePathFromAbsolutePath(){
        return absolutePath;
    }

    /**
     * Return the path of resource given from relative path read in the properties
     * @return path of resource given
     *
     * @param nameResource name of resource for get
     * @SEE {@link FileUtil#getCurrentWorking(String)}
     *
     * Other option:
     *  return Path.of(getResourcePathFromRelativePathGivenNameResource().toString(),nameResource);
     */
    public Path getdResourcePathFromRelativePathGivenNameResource(@Nonnull String nameResource){
        return buildResourcePathFromRelativePathGivenNameResource().resolve(nameResource);

    }
}
