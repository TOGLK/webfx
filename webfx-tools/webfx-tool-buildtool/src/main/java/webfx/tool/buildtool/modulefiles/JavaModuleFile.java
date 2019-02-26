package webfx.tool.buildtool.modulefiles;

import webfx.tool.buildtool.Module;
import webfx.tool.buildtool.Platform;
import webfx.tool.buildtool.ProjectModule;
import webfx.tool.buildtool.util.reusablestream.ReusableStream;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

/**
 * @author Bruno Salmon
 */
public class JavaModuleFile extends ModuleFile {

    public JavaModuleFile(ProjectModule module) {
        super(module);
    }

    @Override
    Path getModulePath() {
        return getModule().getJavaSourceDirectory().resolve("module-info.java");
    }

    String getJavaModuleName() {
        return getJavaModuleName(getModule());
    }

    @Override
    void readFile() {
    }

    @Override
    public void writeFile() {
        StringBuilder sb = new StringBuilder("// This java module file was generated by WebFx\n\nmodule ").append(getJavaModuleName()).append(" {\n");
        sb.append("\n    // Direct dependencies modules\n");
        ProjectModule module = getModule();
        module.getDirectDependencies()
                .map(JavaModuleFile::getJavaModuleName)
                .distinct()
                .forEach(m -> sb.append("    requires ").append(m).append(";\n"));
        sb.append("\n    // Exported packages\n");
        module.getDeclaredJavaPackages().forEach(p -> sb.append("    exports ").append(p).append(";\n"));
        ReusableStream<String> usedJavaServices = module.getUsedJavaServices();
        if (usedJavaServices.count() > 0) {
            sb.append("\n    // Used services\n");
            usedJavaServices.forEach(s -> sb.append("    uses ").append(s).append(";\n"));
        }
        ReusableStream<String> providedJavaServices = module.getProvidedJavaServices();
        if (module.getTarget().isPlatformSupported(Platform.JRE) && providedJavaServices.count() > 0) {
            sb.append("\n    // Provided services\n");
            providedJavaServices.forEach(s -> sb.append("    provides ").append(s).append(" with ").append(module.getProvidedJavaServiceImplementations(s).collect(Collectors.joining(", "))).append(";\n"));
        }
        sb.append("\n}");

        Path path = getModulePath();
        try {
            //Files.createDirectories(path.getParent()); // Creating all necessary directories
            BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"));
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getJavaModuleName(Module module) {
        String artifactId = module.getArtifactId();
        switch (artifactId) {
            case "webfx-fxkit-emul-javafxbase" :        return "javafx.base";
            case "webfx-fxkit-emul-javafxcontrols" :    return "javafx.controls";
            case "webfx-fxkit-emul-javafxgraphics" :    return "javafx.graphics";
            default: return artifactId.replaceAll("[^a-zA-Z0-9]", ".");
        }
    }
}
