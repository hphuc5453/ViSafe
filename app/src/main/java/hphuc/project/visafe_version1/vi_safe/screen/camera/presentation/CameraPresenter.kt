package hphuc.project.visafe_version1.vi_safe.screen.camera.presentation

import hphuc.project.visafe_version1.vi_safe.app.presentation.navigater.AndroidScreenNavigator

class CameraPresenter(private val screenNavigator: AndroidScreenNavigator) : CameraContract.Presenter() {

    override fun gotoCameraActivity() {
        screenNavigator.gotoCameraActivity()
    }
}