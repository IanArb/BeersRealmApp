import Foundation
import Combine
import SwiftUI

struct ImageView: View {
    @ObservedObject var imageLoader:ImageLoader
    @State var image:UIImage = UIImage()
    var contentMode: ContentMode

    init(withURL url:String, contentMode: ContentMode) {
        imageLoader = ImageLoader(urlString:url)
        self.contentMode = contentMode
    }

    var body: some View {
        VStack {
            Image(uiImage: image)
                .resizable()
                .aspectRatio(contentMode: contentMode)
                .cornerRadius(6)
        }.onReceive(imageLoader.didChange) { data in
            self.image = UIImage(data: data) ?? UIImage()
        }
    }
}

class ImageLoader: ObservableObject {
    var didChange = PassthroughSubject<Data, Never>()
    var data = Data() {
        didSet {
            didChange.send(data)
        }
    }

    init(urlString:String) {
        guard let url = URL(string: urlString) else { return }
        let task = URLSession.shared.dataTask(with: url) { data, response, error in
            guard let data = data else { return }
            DispatchQueue.main.async {
                self.data = data
            }
        }
        task.resume()
    }
}
