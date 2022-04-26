import SwiftUI
import shared


struct ContentView: View {
    
    var body: some View {
        NavigationView {
            BeersView()
                .navigationBarTitle("Beers")
                .navigationBarItems(trailing:
                        Button(action: {
                    
                    }) {
                        NavigationLink(destination: AddBeerView()) {
                            Image(systemName: "plus").imageScale(.large)
                        }
                    }
                )
        }
    }
}



struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
