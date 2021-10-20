//
//  ContentView.swift
//  YocShowcaseSwiftUI
//
//  Created by Stefan Markovic on 27.7.21.
//

import SwiftUI

struct ContentView: View {
    var images = YocImages.yocImagesList
    
    var body: some View {
        NavigationView {
            ZStack {
                List(images, id: \.id) { image in
                    if (image.title == "YOC Mystery Ad") {
                        NavigationLink(
                            destination: MysteryView(),
                            label: {
                                Image(image.imageName)
                                    .resizable()
                                    .scaledToFit()
                                    .frame(height: 70)
                                    .padding()
                                    .border(Color.secondary)
                                VStack(alignment: .leading, spacing: 5) {
                                    Text(image.title)
                                        .fontWeight(.bold)
                                        .minimumScaleFactor(0.5)
                                        .font(.subheadline)
                                    Text(image.description)
                                        .lineLimit(4)
                                        .font(.subheadline)
                                        .foregroundColor(.secondary)
                                        .padding(.vertical, 10)
                                }
                            })
                    } else {
                        NavigationLink(destination: CreativeView(navTitle: image.title), label: {
                            Image(image.imageName)
                                .resizable()
                                .scaledToFit()
                                .frame(height: 70)
                                .padding()
                                .border(Color.secondary)
                            VStack(alignment: .leading, spacing: 5) {
                                Text(image.title)
                                    .fontWeight(.bold)
                                    .minimumScaleFactor(0.5)
                                    .font(.subheadline)
                                Text(image.description)
                                    .lineLimit(4)
                                    .font(.subheadline)
                                    .foregroundColor(.secondary)
                                    .padding(.vertical, 10)
                            }
                        })
                    }
                }
                .navigationTitle("Creatives")
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
