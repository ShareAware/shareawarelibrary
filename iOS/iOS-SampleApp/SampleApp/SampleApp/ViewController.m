//
//  ViewController.m
//  SampleApp
//
//

#import "ViewController.h"
#import "ShareWareLibrary.h"

@interface ViewController ()

@property (strong, nonatomic) NSMutableArray                    *artifactsArray;
@property (strong, nonatomic) ShareWareLibrary                  *shareWareLibrary;

@end

@implementation ViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    NSString *defaults = ([self.isFreeOrPaidSegControl selectedSegmentIndex] == 0) ? @"false" : @"true";
    NSArray *artifactNames = [NSArray arrayWithObjects:@"Feature 1",
                              @"Feature 2",
                              @"Document 1",
                              @"Asset 1",
                              @"Image 1",
                              @"Video 1",
                              @"Music 1",
                              nil];
    NSArray *artifactPerms  = [NSMutableArray arrayWithObjects:defaults,
                               defaults,
                               defaults,
                               defaults,
                               defaults,
                               defaults,
                               defaults,
                               nil];
    _artifactsArray = [[NSMutableArray alloc] init];
    for (int ii = 0; ii < [artifactNames count]; ii++) {
        ShareWareArtifact *swf = [[ShareWareArtifact alloc] init];
        swf.name = [artifactNames objectAtIndex:ii];
        swf.permission = [artifactPerms objectAtIndex:ii];
        [_artifactsArray addObject:swf];
    }
    [self setPerms: defaults];
}

- (void) viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    [self setupUI];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)handleShareButtonItem:(id)sender {
    _shareWareLibrary = [[ShareWareLibrary alloc] init];
    _shareWareLibrary.isFree = ([self.isFreeOrPaidSegControl selectedSegmentIndex] == 0);
    _shareWareLibrary.sDelegate = self;
    
    _shareWareLibrary.artifactsArray = _artifactsArray;
    
    [_shareWareLibrary launchUI:self];
}

- (void) setupUI {
    NSArray *buttons = [NSArray arrayWithObjects:self.feature1Button,
                        self.feature2Button,
                        self.document1Button,
                        self.asset1Button,
                        self.image1Button,
                        self.video1Button,
                        self.music1Button,
                        nil];
    
    for (int ii = 0; ii < [buttons count]; ii++) {
        UIButton *button = [buttons objectAtIndex:ii];
        button.tag = 1000 + ii;
        ShareWareArtifact *swf = [_artifactsArray objectAtIndex:ii];
        [button setEnabled:[swf.permission isEqualToString:@"true"]];
        [button addTarget:self action:@selector(handleButton:) forControlEvents:UIControlEventTouchUpInside];
    }
}

- (void) handleButton : (id) sender {
    UIButton *button = (UIButton *) sender;
    NSArray *messageStrings = [NSArray arrayWithObjects:@"Feature 1 is available",
                               @"Feature 2 is available",
                               @"Document 1 can be seen",
                               @"Asset 1 button can seen",
                               @"Image 1 can be viewed",
                               @"Video 1 can be viewed",
                               @"Music 1 can be heared",
                               nil];
    int tag = button.tag - 1000;
    UIAlertView * alert = [[UIAlertView alloc] initWithTitle:@"Feature Function" message:[messageStrings objectAtIndex:tag] delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
    [alert show];
}

- (IBAction)handleFreePaidSegControl:(id)sender {
    UISegmentedControl *sc = (UISegmentedControl *) sender;
    NSString *isPaid = ([sc selectedSegmentIndex] == 1) ? @"true" : @"false";
    [self setPerms : isPaid];
    [self setupUI];
}

- (void) setPerms : (NSString *) defaults {
    for (ShareWareArtifact *swa in _artifactsArray) {
        swa.permission = defaults;
    }
}

#pragma mark - ShareWareLibraryDelegate delegate

- (void) updatePermission : (NSString *) key : (NSString *) value : (NSString *) sharedWith {
    // Don't mess unless its free
    if ([self.isFreeOrPaidSegControl selectedSegmentIndex] == 0) {
        NSLog(@"updatePermission: %@, %@", key, value);
        int idx = -1;
        int ii = 0;
        ShareWareArtifact *tSwa = nil;
        for (ShareWareArtifact *swa in _artifactsArray) {
            if ([swa.name isEqualToString:key]) {
                swa.permission = value;
                swa.sharedWith = sharedWith;
                idx = ii;
                tSwa = swa;
                break;
            }
            ii++;
        }
        if (idx > 0) {
            [_artifactsArray replaceObjectAtIndex:idx withObject:tSwa];
            // [self.artifactsTableView reloadData];
        }
        [self viewWillAppear:TRUE];
    }
}

@end
