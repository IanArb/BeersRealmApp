import SwiftUI
import shared

struct BeersView: View {
    @ObservedObject var viewModel = BeersViewModel(beersDatabase: BeersDatabase())
    
    var body: some View {
        switch viewModel.state {
        case .idle:
            Color.clear.onAppear(perform: viewModel.loadBeers)
        case .success(let data):
            ScrollView(showsIndicators: false) {
                Text("Clear All")
                    .fontWeight(.medium)
                    .foregroundColor(.blue)
                    .onTapGesture {
                        viewModel.removeAllBeers()
                    }
                ForEach(data, id: \.id) { beer in
                    Card(beer: beer) {
                        viewModel.removeBeerById(id: beer.id)
                    }
                }
            }
            .onDisappear {
                viewModel.cancelRemoveHandler()
            }
        case .empty:
            VStack(alignment: .center) {
                Text("There are no beers here.. add some. I'm thirsty!")
            }
            .onDisappear {
                viewModel.cancelRemoveHandler()
            }
        case .error:
            VStack(alignment: .center) {
                Text("We've drank all the beer. Sorry!")
            }
            .onDisappear {
                viewModel.cancelRemoveHandler()
            }
        }
    }
}

struct Card: View {
    var beer: BeerData
    var onDeleteBeer: () -> Void
    
    var body: some View {
        ZStack(alignment: .leading) {
            RoundedRectangle(cornerRadius: 6, style: .continuous)
                .fill(Color.white)
                .shadow(color: Color.black.opacity(0.11), radius: 8, x: 0.0, y: 7)
                .frame(width: .infinity)
                .padding(.leading, 20)
                .padding(.trailing, 20)
                .padding(.top, 10)
                .padding(.bottom, 10)
            
            VStack(alignment: .leading) {
                VStack {
                    Image(systemName: "xmark")
                        .font(.system(size: 12))
                        .onTapGesture {
                            onDeleteBeer()
                        }
                }
                .background(
                    Circle().fill(Color(red: 242 / 255, green: 242 / 255, blue: 242 / 255))
                )
                .padding(.leading, 40)
                
                HStack {
                    VStack(alignment: .leading) {
                        ImageView(withURL: beer.imageUrl, contentMode: ContentMode.fit)
                            .frame(height: 120)
                    }
                    .padding(.top, 4)
                    .padding(.leading, 45)
                    .padding(.trailing, 30)
                    
                    VStack(alignment: .leading) {
                        Text(beer.name)
                            .font(.title3)
                            .fontWeight(.medium)
                            .fixedSize(horizontal: false, vertical: true)
                        
                        Text(beer.brewery)
                            .font(.caption)
                            .foregroundColor(Color.black)
                        
                        Text("ABV - " + String(beer.abv))
                            .font(.title3)
                            .foregroundColor(Color.black)
                    }
                    
                }
            }
            .padding(.top, 30)
            .padding(.bottom, 30)

        }
    }
}

struct BeersView_Previews: PreviewProvider {
    static var previews: some View {
        BeersView()
    }
}
