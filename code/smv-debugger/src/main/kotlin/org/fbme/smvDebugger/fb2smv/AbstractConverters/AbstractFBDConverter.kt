package org.fbme.smvDebugger.fb2smv.AbstractConverters

import jetbrains.mps.project.MPSProject
import org.fbme.lib.iec61499.declarations.CompositeFBTypeDeclaration
import org.fbme.lib.iec61499.descriptors.FBTypeDescriptor
import org.fbme.lib.iec61499.fbnetwork.FunctionBlockDeclaration
import java.io.File
import java.nio.file.Path
import kotlin.io.path.pathString

/*
TODO
 1)INIT[...] - on ECC transitions
 2)REQ AND (PREV = INP) - on ECC transitions
 3) generateNA - when NA may be grater then 1
 4) fill maps
 5) generateOutputVariablesUpdate - Iterate over algs 2d output generation
 6) names of composite blocks???

 */
abstract class AbstractFBDConverter(val fileExtention: String) {
    val fbTypesSet = HashSet<FBTypeDescriptor>()
    val buf = StringBuilder()
    var basicFBConverter: AbstractBasicFBConverter? = null
    var compositeFBConverter: AbstractCompositeFBConverter? = null

    private fun networkTraversal(compositeFb: CompositeFBTypeDeclaration){
        if( fbTypesSet.contains(compositeFb.typeDescriptor)) return
        val fbs: List<FunctionBlockDeclaration> = compositeFb.network.functionBlocks
        for (fb in fbs) {
            //if we have already generated definition for this FB type
            if(fbTypesSet.contains(fb.type)) continue
            //composite or not?
            if (fb.typeReference is CompositeFBTypeDeclaration){
                networkTraversal(fb.typeReference as CompositeFBTypeDeclaration)
            }
            fbTraversal(fb.type)
            fbTypesSet.add(fb.type)
        }
    }

    private fun fbTraversal(fb: FBTypeDescriptor) {
        basicFBConverter?.generateSignature(fb, buf)
        basicFBConverter?.generateLocalVariableDeclaration(fb, buf)
        basicFBConverter?.generateCountersDeclaration(fb, buf)
        basicFBConverter?.generateLocalVariableDefinition(fb, buf)
        basicFBConverter?.generateECCTransitions(fb, buf)
        basicFBConverter?.generateOSM(fb, buf)
        basicFBConverter?.generateNA(fb, buf)
        basicFBConverter?.generateNI(fb, buf)
        basicFBConverter?.generateInputVariablesUpdate(fb, buf)
        basicFBConverter?.generateOutputVariablesUpdate(fb, buf)
        basicFBConverter?.generateAlphaBeta(fb, buf)
        basicFBConverter?.generateInputEventsReset(fb, buf)
        basicFBConverter?.generateOutputEventsSet(fb, buf)
        basicFBConverter?.generateFooter(fb, buf)
    }

    fun convertFB(
        fbPath: Path, compositeFb: CompositeFBTypeDeclaration,
        project: MPSProject
    ): Path? {
        project.modelAccess.runReadAction {
            val file = File(
                    fbPath.pathString.substring(0, fbPath.pathString.lastIndexOf("."))
                            + compositeFb.name + "." + fileExtention)

            networkTraversal(compositeFb)
            val fbs: List<FunctionBlockDeclaration> = compositeFb.network.functionBlocks
            val dataCon = compositeFb.network.dataConnections
            val eventCon = compositeFb.network.eventConnections
            for (fb in fbs) {

            }
            file.writeText(buf.toString())
            buf.clear()
        }
        return null
    }


}