package smvDebugger.plugin;

/*Generated by MPS */

import jetbrains.mps.plugins.actions.GeneratedActionGroup;
import org.jetbrains.annotations.Nullable;
import jetbrains.mps.workbench.action.ApplicationPlugin;

public class SmvDebuggerGroup_ActionGroup extends GeneratedActionGroup {
  public static final String ID = "smvDebugger.plugin.SmvDebuggerGroup_ActionGroup";

  public SmvDebuggerGroup_ActionGroup(@Nullable ApplicationPlugin plugin) {
    super("SmvDebuggerGroup", ID, plugin);
    setIsInternal(false);
    setPopup(false);
    SmvDebuggerGroup_ActionGroup.this.addAction("smvDebugger.plugin.Debug_SMV_Action");
  }
}
