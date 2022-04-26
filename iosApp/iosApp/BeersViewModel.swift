import Foundation
import shared
import KMPNativeCoroutinesAsync

class BeersViewModel : ObservableObject {
    
    private let beersDatabase: BeersDarabase
    
    init(beersDatabase: BeersDatabase) {
        self.beersDatabase = beersDatabase
    }
    
    private var handler: Task<(), Never>? = nil
    
    enum State {
        case success([BeerData])
        case empty
        case error
        case idle
    }
    
    @Published private(set) var state = State.idle
    
    func loadBeers() {
        handler = Task {
            do {
                let stream = asyncStream(for: beersDatabase.findAllBeersNative())
                for try await beers in stream {
                    if (beers.isEmpty) {
                        self.state = .empty
                    } else {
                        self.state = .success(beers)
                    }
                }
            } catch {
                self.state = .error
            }
        }
    }
    
    func saveBeer(data: BeerData) {
        beersDatabase.saveBeer(beer: data)
    }
    
    func removeBeerById(id: String) {
        beersDatabase.removeBeerById(id: id)
    }
    
    func removeAllBeers() {
        beersDatabase.removeAllBeers()
    }
    
    func cancel() {
        handler?.cancel()
    }
}
