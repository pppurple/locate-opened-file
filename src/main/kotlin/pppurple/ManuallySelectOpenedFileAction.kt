package pppurple

import com.intellij.ide.SelectInTarget
import com.intellij.ide.SmartSelectInContext
import com.intellij.ide.projectView.ProjectView
import com.intellij.ide.projectView.impl.ProjectViewImpl
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager

/**
 * Main action
 * @See: [SelectFileAction.getView]
 * @See: [ProjectViewImpl.MyAutoScrollFromSourceHandler.findSelectInContext]
 */
class ManuallySelectOpenedFileAction : AnAction() {
    override fun actionPerformed(actionEvent: AnActionEvent) {
        val project = actionEvent.project ?: return
        val projectView = getView(project) ?: return
        val projectViewPane = projectView.currentProjectViewPane ?: return

        val fileEditorManager = FileEditorManager.getInstance(project)
        val psiFile = getPsiFile(project, getVirtualFile(fileEditorManager.selectedEditor)) ?: return

        /**
         * Can't use [ProjectViewImpl.SimpleSelectInContext] directly because it's private.
         * So use [SmartSelectInContext] instead.
         */
        val context = SmartSelectInContext(psiFile, psiFile)
        val target: SelectInTarget = projectViewPane.createSelectInTarget()
        context.selectIn(target, false)
    }

    private fun getView(project:Project) :ProjectViewImpl? {
        return project.let { ProjectView.getInstance(it) as? ProjectViewImpl }
    }

    private fun getPsiFile(myProject: Project, file: VirtualFile?): PsiFile? {
        return if (file == null || !file.isValid) null else PsiManager.getInstance(myProject).findFile(file)
    }

    private fun getVirtualFile(fileEditor: FileEditor?): VirtualFile? {
        return fileEditor?.file
    }
}
