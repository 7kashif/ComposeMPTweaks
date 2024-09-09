//
//  LottieView.swift
//  iosApp
//
//  Created by Kashif Masood on 06/09/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Lottie
import SwiftUI

struct LottieView: UIViewRepresentable {
    
    var animationName: String
    var loopMode: LottieLoopMode = .loop
    
    class Coordinator: NSObject {
        var parent: LottieView
        
        init(parent: LottieView) {
            self.parent = parent
        }
    }
    
    func makeCoordinator() -> Coordinator {
        Coordinator(parent: self)
    }
    
    func makeUIView(context: Context) -> some UIView {
        let view = UIView(frame: .zero)
        let animationView = LottieAnimationView(name: animationName)
        animationView.loopMode = loopMode
        animationView.contentMode = .scaleAspectFit
        animationView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(animationView)
        
        NSLayoutConstraint.activate([
            animationView.heightAnchor.constraint(equalTo: view.heightAnchor),
            animationView.widthAnchor.constraint(equalTo: view.widthAnchor)
        ])
        
        animationView.play()
        return view
    }
    
    func updateUIView(_ uiView: UIViewType, context: Context) {
        // You can update the view if needed, such as reloading animations.
    }
}
