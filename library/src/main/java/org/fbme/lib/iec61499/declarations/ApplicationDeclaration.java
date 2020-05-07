package org.fbme.lib.iec61499.declarations;

import org.fbme.lib.iec61499.fbnetwork.FBNetwork;
import org.jetbrains.annotations.NotNull;

public interface ApplicationDeclaration extends NamedDeclaration {

    @NotNull FBNetwork getNetwork();

    @NotNull SystemDeclaration getSytem();
}
