package com.anchorclient.client.feature.module;

import com.anchorclient.client.feature.module.impl.hud.*;
import com.anchorclient.client.feature.module.impl.mechanic.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ModuleManager {

    private CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();

    /**
     * Initializes all the modules.
     */
    public ModuleManager() {
        addModules(
                // HUD
                new FPS(),
                new CPS(),
                new Keystrokes(),
                new ArmorStatus(),
                new Ping(),
                new Crosshair(),
                // new FPSSpoofer(),
                new ClickGuiModule(),

                // SERVER

                // MECHANIC
                new AutoSprint(),
                new OldVisuals(),
                new ItemPhysics(),
                new FullBright(),
                new NickHider(),
                new Zoom(),
                new Cosmetics(),
                new FullBright()
        );
    }

    /**
     * Adds the specified modules to the client.
     * @param modules The specified modules to add.
     */
    public void addModules(Module... modules) {
        this.modules.addAll(Arrays.asList(modules));
    }

    /**
     * Returns all the modules.
     * @return modules.
     */
    public List<Module> getModules() {
        return this.modules;
    }

    /**
     * @param clazz The class of the module to return
     * @return The module retrieved.
     */
    public Module getModule(Class<?> clazz) {
        return modules.stream().filter(m -> clazz.equals(m.getClass())).findFirst().orElse(null);
    }

    /**
     * Returns a module by its name
     * @param module Name of module.
     * @return The module.
     */
    public Module getModuleByName(String module) {
        return this.getModules().stream().filter(m -> module.equalsIgnoreCase(m.getName())).findFirst().orElse(null);
    }

    /**
     * Returns all the modules inside a certain category.
     * @param c The Category to search.
     * @return Retrieved modules.
     */
    public List<Module> getModulesInCategory(Category c) {
        if(c == Category.ALL) {
            return getModules();
        }

        return this.modules.stream().filter(m -> m.getCategory() == c).collect(Collectors.toList());
    }

    /**
     * Returns all the current enabled modules.
     * @return The modules.
     */
    public List<Module> getEnabledModules() {
        return this.getModules().stream().filter(Module::isEnabled).collect(Collectors.toList());
    }

}
