import Foundation
import shared
import KMPNativeCoroutinesAsync

class BeersViewModel : ObservableObject {
    
    private let beersDatabase: BeersDatabase
    
    init(beersDatabase: BeersDatabase) {
        self.beersDatabase = beersDatabase
    }
    
    private var handler: Task<(), Never>? = nil
    private var saveHandler: Task<(), Never>? = nil
    private var removeHandler: Task<(), Never>? = nil
    private var removeAllHandler: Task<(), Never>? = nil
    
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
        saveHandler = Task {
            do {
                _ = try await asyncFunction(for: beersDatabase.saveBeerNative(beer: data))
            } catch {
                self.state = .error
            }
        }
    }
    
    func removeBeerById(id: String) {
        removeHandler = Task {
            do {
                _ = try await asyncFunction(for: beersDatabase.removeBeerByIdNative(id: id))
            } catch {
                self.state = .error
            }
        }
    }
    
    func removeAllBeers() {
        removeAllHandler = Task {
            do {
                _ = try await asyncFunction(for: beersDatabase.removeAllBeersNative())
            } catch {
                self.state = .error
            }
        }
    }
    
    func cancelSaveHandler() {
        saveHandler?.cancel()
    }
    
    func cancelRemoveHandler() {
        removeHandler?.cancel()
        removeAllHandler?.cancel()
    }
    
}
