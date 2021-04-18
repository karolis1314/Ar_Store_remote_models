package com.example.clickeventciew

import android.content.Intent
import android.media.CamcorderProfile
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_ar_scene.*

class arScene : AppCompatActivity() {

    private lateinit var arFragment : ArFragment

    private lateinit var photoSaver: PhotoSaver
    private lateinit var videoRecorder: VideoRecorder

    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar_scene)

        arFragment = fragment as ArFragment

        photoSaver = PhotoSaver(this)

        setupFab()
        setupFab2()

        videoRecorder = VideoRecorder(this).apply {
            sceneView = arFragment.arSceneView
            setVideoQuality(CamcorderProfile.QUALITY_1080P, resources.configuration.orientation)
        }
        arFragment.setOnTapArPlaneListener{
                hitResult, _, _ ->
            spawnObject(hitResult.createAnchor(), Uri.parse(intent.getStringExtra("url")))
        }


    }


    private fun spawnObject(anchor: Anchor, modelUri: Uri){
        val renderableSources = RenderableSource.builder()
            .setSource(this, modelUri, RenderableSource.SourceType.GLB)
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()
        ModelRenderable.builder()
            .setSource(this, renderableSources)
            .setRegistryId(modelUri)
            .build()
            .thenAccept {
                addNodeToScene(anchor, it)

            }.exceptionally {
                Log.e("Main Activity", "Error loading model", it)
                null
            }
    }

    private fun addNodeToScene(anchor: Anchor, modelRenderable: ModelRenderable){
        val anchorNode = AnchorNode(anchor)

        TransformableNode(arFragment.transformationSystem).apply {
            renderable = modelRenderable
            setParent(anchorNode)
        }

        arFragment.arSceneView.scene.addChild(anchorNode)


    }

    private fun setupFab() {
        fab.setOnClickListener {
            if(!isRecording) {
                photoSaver.takePhoto(arFragment.arSceneView)
            }
        }
        fab.setOnLongClickListener {
            isRecording = videoRecorder.toggleRecordingState()
            true
        }
        fab.setOnTouchListener { view, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_UP && isRecording) {
                isRecording = videoRecorder.toggleRecordingState()
                Toast.makeText(this, "Saved video to gallery!", Toast.LENGTH_LONG).show()
                true
            } else false
        }
    }

    private fun setupFab2(){
        fab2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
