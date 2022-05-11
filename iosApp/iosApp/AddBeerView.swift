import SwiftUI
import shared

struct AddBeerView: View {
    @State private var beerName: String = ""
    @State private var brewery: String = ""
    @State private var abv: String = ""
    @State private var selection: Int? = nil
    
    @ObservedObject var viewModel = BeersViewModel(beersDatabase: BeersDatabase())
    
    var body: some View {
        VStack(alignment: .leading) {
            TextField("Enter Beer name", text: $beerName).textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.top, 4)
            TextField("Enter Brewery name", text: $brewery).textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.top, 4)
            TextField("Enter abv", text: $abv).textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.top, 4)
            
            Button(action: {
                viewModel.saveBeer(data: BeerData(
                        id: UUID().uuidString,
                        name: beerName,
                        brewery: brewery,
                        imageUrl: "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2FElevation_pale_ale.png?alt=media&token=e5fbe476-dfeb-41ac-87d8-c2698099313c",
                        abv: Int32(abv) ?? Int32(0)
                    )
                )
            }) {
                HStack {
                    Text("Done")
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                        .background(Color(red: 242 / 255, green: 153 / 255, blue: 74 / 255))
                        
                }
                .padding(10)
                
            }
            .frame(width: 380)
            .background(
                RoundedRectangle(cornerRadius: 6.0, style: .continuous)
                    .fill(Color.orange)
            )
            .padding(.bottom, 16)
            .padding(.top, 4)

           
            
            Spacer()
        }
        .navigationBarTitle("Add beer", displayMode: .inline)
        .padding(16)
        .onDisappear {
            viewModel.cancelSaveHandler()
        }
    }
}

struct AddBeerView_Previews: PreviewProvider {
    static var previews: some View {
        AddBeerView()
    }
}
